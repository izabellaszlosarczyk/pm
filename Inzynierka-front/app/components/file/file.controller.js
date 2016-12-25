export default class FileController {
  constructor(usersService, $state, $http, tokenService, $rootScope,$scope, $stateParams) {
    this.usersService = usersService;
    this.tokenService = tokenService;
    this.$rootScope = $rootScope;
    this.$scope = $scope;
    this.stateParams = $stateParams;
    this.state = $state;
    this.loading = false;
    this.$http = $http;
    this.fileDetails = [];
    this.content;
    this.comment = "";
    this.userFilesSaved = [];
    this.userFilesSubs = [];
    this.flagType = false; // if text - true  <--> image -false
    this.flagIs = false;
    this.flagTmp = ""; // flagi typu : "barChart", "dendo", "graph", "radial", "straight"n


    this.jsonToVisualisation; // w tym pliku masz tego jsona z backednu
  }
  $onInit() {
    console.log("test dendo");
    if (this.fileDetails.type != "image" && this.fileDetails.type != "chart"){
      this.flagType = true;

    }
    this.jsonToVisualisation = this.usersService.jsonToVisualisation;
    console.log(this.jsonToVisualisation);
    this.flagTmp = this.usersService.analysesType;
    //obrazek
    var tmp = new Object();
    //this.fileDetails = this.usersService.requestedFileDetails;
    tmp = this.usersService.requestedFileDetails;
    this.fileDetails = tmp;
    console.log(this.fileDetails);
    console.log("TERAZ CZESC UZYTKOWNIKA");
    this.userFilesSubs = this.usersService.userData.subscribedFiles;
    this.userFilesSaved = this.usersService.userData.savedFiles;
    console.log(this.userFilesSubs);
    console.log(this.userFilesSaved);
    console.log(this.fileDetails.title);
    if ((this.userFilesSaved.indexOf(this.fileDetails.title) > -1) || ( this.userFilesSubs.indexOf(this.fileDetails.title) > -1)) {
      this.flagIs = true;
      console.log("okej");
    }

    this.content = this.usersService.getUrl(`/content/load/${this.fileDetails.title}`);
    // this.usersService.getFile(this.fileDetails.title).then(function successCallback(response, status, headers, config) {
    //   this.loading = false;
    //   console.log(response);
    // }.bind(this));

    // this.usersService.getFile(this.usersService.requestedFileName).then(function successCallback(response, status, headers, config) {
    //   //dane
    //   this.usersService.getFilesDetails(this.usersService.requestedFileName).then(function successCallback(response2, status, headers, config) {
    //     this.loading = false;
    //     this.fileDetails= response.data;
    //     //console.log(this.filesDetails);
    //   }.bind(this));}.bind(this));
    if (this.flagTmp == "straight") {
      var div = d3.select("body").append("div")
          .attr("class", "tooltip")
          .style("opacity", 0);
      var width = 1960,
          height = 1400;

      var force = d3.layout.force()
          .size([width, height])
          .charge(-200)
          .linkDistance(20)
          .on("tick", tick);

      var drag = force.drag()
          .on("dragstart", dragstart);

      var svg = d3.select("#straight").append("svg")
          .attr("width", width)
          .attr("height", height);

      //TODO: tutaj bedzie
      //d3.json(this.jsonToVisualisation, function(error, graph)
      d3.json("./assets/graph.json", function(error, graph) {
        if (error) throw error;
        var nodes = graph.nodes,
            nodeById = d3.map(nodes, function(d) { return d.id; }),
            links = graph.links,
            bilinks = [];

        link = svg.selectAll(".link")
            .data(links)
            .enter().append("line")
            .attr("class", "link")
            .attr("stroke-width", function(d) {
              return (d.quantity)/5;
            });

        node = svg.selectAll(".node")
            .data(nodes.filter(function(d) { return d.id; }))
            .enter().append("circle")
            .attr("class", "node")
            .attr("r", function(d) { return d.quantity/20;})
            .on("dblclick", dblclick)
            .call(drag)
            .on("mouseover", function(d) {
              div.transition()
                  .duration(200)
                  .style("opacity", .9);
              div.html(d.txt)
                  .style("left", (d3.event.pageX- 100) + "px")
                  .style("top", (d3.event.pageY - 100) + "px");
            })
            .on("mouseout", function(d) {
              div.transition()
                  .duration(500)
                  .style("opacity", 0);
            })
//                .attr("fill", function(d) { return color(d.group); })

        text = svg.selectAll(".text")
            .data(nodes.filter(function(d) { return d.id; }))

            .enter().append("text")
            .attr("fill", "#07dfff")
            .text(function(d) {
              return d.id;  });
        force
            .nodes(graph.nodes)
            .links(graph.links)
            .start();

      });

      function tick() {
        link.attr("x1", function(d) { return d.source.x; })
            .attr("y1", function(d) { return d.source.y; })
            .attr("x2", function(d) { return d.target.x; })
            .attr("y2", function(d) { return d.target.y; });
        node.attr("cx", function(d) { return d.x; })
            .attr("cy", function(d) { return d.y; });

        text.attr("transform", positionText);
      }

      function positionText(d) {
        return "translate(" + d.x + "," + d.y + ")";
      }

      function dblclick(d) {
        d3.select(this).classed("fixed", d.fixed = false);
      }

      function dragstart(d) {
        d3.select(this).classed("fixed", d.fixed = true);
      }
    }
    if (this.flagTmp == "graph") {
      var div = d3.select("body").append("div")
          .attr("class", "tooltip")
          .style("opacity", 0);
      var width = 1960,
          height = 1400;

      var force = d3.layout.force()
          .size([width, height])
          .charge(-200)
          .linkDistance(20)
          .on("tick", tick);

      var drag = force.drag()
          .on("dragstart", dragstart);

      var svg = d3.select("#graph").append("svg")
          .attr("width", width)
          .attr("height", height);

      d3.json("../generated_data/graph.json", function(error, graph) {
        if (error) throw error;
        var nodes = graph.nodes,
            nodeById = d3.map(nodes, function(d) { return d.id; }),
            links = graph.links,
            bilinks = [];


        links.forEach(function(link) {
          var s = link.source =nodeById.get(link.source),
              t = link.target = nodeById.get(link.target),
              i ={quantity: link.quantity, time: link.time}; // intermediate node
          nodes.push(i);
          links.push({source: s, target: i}, {source: i, target: t});
          bilinks.push([s, i, t]);
        });

        link = svg.selectAll(".link")
            .data(bilinks)
            .enter().append("path")
            .attr("class", "link")
            .attr("stroke-width", function(d) {
              return (d[1].quantity)/5;
            });

//        link = svg.selectAll(".link")
//                .data(links)
//                .enter().append("line")
//                .attr("class", "link")
//                .attr("stroke-width", function(d) {
//                    return (d.quantity)/5;
//                });

        node = svg.selectAll(".node")
            .data(nodes.filter(function(d) { return d.id; }))
            .enter().append("circle")
            .attr("class", "node")
            .attr("r", function(d) { return d.quantity/20;})
            .on("dblclick", dblclick)
            .call(drag)
            .on("mouseover", function(d) {
              div.transition()
                  .duration(200)
                  .style("opacity", .9);
              div.html(d.txt)
                  .style("left", (d3.event.pageX- 100) + "px")
                  .style("top", (d3.event.pageY - 100) + "px");
            })
            .on("mouseout", function(d) {
              div.transition()
                  .duration(500)
                  .style("opacity", 0);
            })
//                .attr("fill", function(d) { return color(d.group); })

        text = svg.selectAll(".text")
            .data(nodes.filter(function(d) { return d.id; }))

            .enter().append("text")
            .attr("fill", "#07dfff")
            .text(function(d) {
              return d.id;  });
        force
            .nodes(graph.nodes)
            .links(graph.links)
            .start();

      });

      function tick() {
//        link.attr("x1", function(d) { return d.source.x; })
//                .attr("y1", function(d) { return d.source.y; })
//                .attr("x2", function(d) { return d.target.x; })
//                .attr("y2", function(d) { return d.target.y; });
        link.attr("d", positionLink);
        node.attr("cx", function(d) { return d.x; })
            .attr("cy", function(d) { return d.y; });

        text.attr("transform", positionText);
      }

      function positionLink(d) {
        return "M" + d[0].x + "," + d[0].y
            + "S" + d[1].x + "," + d[1].y
            + " " + d[2].x + "," + d[2].y;
      }

      function positionText(d) {
        return "translate(" + d.x + "," + d.y + ")";
      }

      function dblclick(d) {
        d3.select(this).classed("fixed", d.fixed = false);
      }

      function dragstart(d) {
        d3.select(this).classed("fixed", d.fixed = true);
      }
    }

    if (this.flagTmp == "radial") {
      var div = d3.select("body").append("div")
          .attr("class", "tooltip")
          .style("opacity", 0);
      var svg = d3.select("svg"),
          width = +svg.attr("width"),
          height = +svg.attr("height"),
          g = svg.append("g").attr("transform", "translate(" + (width / 2 - 15) + "," + (height / 2 + 25) + ")");

      var stratify = d3.stratify()
          .parentId(function(d) { return d.id.substring(0, d.id.lastIndexOf(".")); });

      var tree = d3.cluster()
          .size([360, 390])
          .separation(function(a, b) { return (a.parent == b.parent ? 1 : 2) / a.depth; });

      d3.csv("./assets/radial_dendrogram.csv", function(error, data) {
        if (error) throw error;

        var root = tree(stratify(data)
            .sort(function(a, b) { return (a.height - b.height) || a.id.localeCompare(b.id); }));

        var link = g.selectAll(".link")
            .data(root.descendants().slice(1))
            .enter().append("path")
            .attr("class", "link")
            .attr("d", function(d) {
              return "M" + project(d.x, d.y)
                  + "C" + project(d.x, (d.y + d.parent.y) / 2)
                  + " " + project(d.parent.x, (d.y + d.parent.y) / 2)
                  + " " + project(d.parent.x, d.parent.y);
            });

        var node = g.selectAll(".node")
            .data(root.descendants())
            .enter().append("g")
            .attr("class", function(d) { return "node" + (d.children ? " node--internal" : " node--leaf"); })
            .attr("transform", function(d) { return "translate(" + project(d.x, d.y) + ")"; })
            .on("mouseover", function(d) {
              if (d.value!=undefined) {
                div.transition()
                    .duration(200)
                    .style("opacity", .9);
                div.html(
                    d.value)
                    .style("left", (d3.event.pageX - 100) + "px")
                    .style("top", (d3.event.pageY - 100) + "px");
              }
            })
            .on("mouseout", function(d) {
              div.transition()
                  .duration(500)
                  .style("opacity", 0);
            });

        node.append("circle")
            .attr("r", 2.5);

        node.append("text")
            .attr("dy", ".31em")
            .attr("x", function(d) { return d.x < 180 === !d.children ? 6 : -6; })
            .style("text-anchor", function(d) { return d.x < 180 === !d.children ? "start" : "end"; })
            .attr("transform", function(d) { return "rotate(" + (d.x < 180 ? d.x - 90 : d.x + 90) + ")"; })
            .text(function(d) { return d.id.substring(d.id.lastIndexOf(".") + 1); });
      });

      function project(x, y) {
        var angle = (x - 90) / 180 * Math.PI, radius = y;
        return [radius * Math.cos(angle), radius * Math.sin(angle)];
      }
    }

    if (this.flagTmp == "barChart") {
      var div = d3.select("body").append("div")
          .attr("class", "tooltip")
          .style("opacity", 0);

      var margin = {top: 20, right: 20, bottom: 70, left: 40},
          width = 3000 - margin.left - margin.right,
          height = 600 - margin.top - margin.bottom;


      var i =0;
      var x = d3.scale.ordinal().rangeRoundBands([0, width], .05);

      var y = d3.scale.linear().range([height, 0]);

      var xAxis = d3.svg.axis()
          .scale(x)
          .orient("bottom")
//            .tickFormat(d3.time.format("%Y-%m"));

      var yAxis = d3.svg.axis()
          .scale(y)
          .orient("left")
          .ticks(10);

      var svg = d3.select("#barChart").append("svg")
          .attr("width", width + margin.left + margin.right)
          .attr("height", height + margin.top + margin.bottom)
          .append("g")
          .attr("transform",
              "translate(" + margin.left + "," + margin.top + ")");

      d3.csv("./assets/bar_chart.csv", function(error, data) {

        data.forEach(function(d) {
          console.log("dupadpfsadsghthersdfafasfsdfdsz");
          d.node = d.node
          d.quantity = +d.quantity
          d.text =  d.text
        });

        x.domain(data.map(function(d) { return d.node; }));
        y.domain([0, d3.max(data, function(d) { return d.quantity; })]);

        svg.append("g")
            .attr("class", "x axis")
            .attr("transform", "translate(0," + height + ")")
            .call(xAxis)
            .selectAll("text")
            .style("text-anchor", "end")
            .attr("dx", "-.8em")
            .attr("dy",
                function(d) {
                  i++;
                  if (i%3==0) {
                    return "1.55em";
                  }
                  else if(i%3==1){
                    return "2.55em";
                  }
                  else
                    return "3.55em";
                })
            .on("mouseover", function(d) {
              div.transition()
                  .duration(200)
                  .style("opacity", .9);
              div.html(d.txt)
                  .style("left", (d3.event.pageX- 100) + "px")
                  .style("top", (d3.event.pageY - 100) + "px");
            })
            .on("mouseout", function(d) {
              div.transition()
                  .duration(500)
                  .style("opacity", 0);
            })
//                .attr("transform", "rotate(-90)" );

        svg.append("g")
            .attr("class", "y axis")
            .call(yAxis)
            .append("text")
            .attr("transform", "rotate(-90)")
            .attr("y", 6)
            .attr("dy", ".71em")

        svg.selectAll("bar")
            .data(data)
            .enter().append("rect")
            .style("fill", "steelblue")
            .attr("x", function(d) { return x(d.node); })
            .attr("width", x.rangeBand())
            .attr("y", function(d) { return y(d.quantity); })
            .attr("height", function(d) { return height
                - y(d.quantity); })
            .on("mouseover", function(d) {
              div.transition()
                  .duration(200)
                  .style("opacity", .9);
              div.html(d.text)
                  .style("left", (d3.event.pageX- 100) + "px")
                  .style("top", (d3.event.pageY - 100) + "px");
            })
            .on("mouseout", function(d) {
              div.transition()
                  .duration(500)
                  .style("opacity", 0);
            })

      });
    }

    if (this.flagTmp == "dendo") {
      var div = d3.select("body").append("div")
          .attr("class", "tooltip")
          .style("opacity", 0);

      var width = 1600,
          height = 1050;

      var cluster = d3.layout.cluster()
          .size([height, width - 160]);

      var diagonal = d3.svg.diagonal()
          .projection(function (d) {
            return [d.y, d.x];
          });

      var svg = d3.select("#dendo").append("svg")
          .attr("width", width)
          .attr("height", height)
          .append("g")
          .attr("transform", "translate(40,0)");

      d3.json("./assets/dendrogram.json", function (error, root) {
        var nodes = cluster.nodes(root),
            links = cluster.links(nodes);

        var link = svg.selectAll(".link")
            .data(links)
            .enter().append("path")
            .attr("class", "link")
            .attr("d", diagonal)
            .attr("stroke", function (d) {
              return 'red';
            })
        var node = svg.selectAll(".node")
            .data(nodes)
            .enter().append("g")
            .attr("class", "node")
            .attr("transform", function (d) {
              return "translate(" + d.y + "," + d.x + ")";
            })

            .on("mouseover", function (d) {
              if (d.txt != undefined) {
                div.transition()
                    .duration(200)
                    .style("opacity", .9);
                div.html(
                    d.txt)
                    .style("left", (d3.event.pageX - 100) + "px")
                    .style("top", (d3.event.pageY - 100) + "px");
              }
            })
            .on("mouseout", function (d) {
              div.transition()
                  .duration(500)
                  .style("opacity", 0);
            });
        node.append("circle")
            .attr("r", 4.5)
            .on("dblclick", dblclick)
        node.append("text")
            .attr("dx", function (d) {
              return d.children ? -10 : 10;
            })
            .attr("dy", 3)
            .style("text-anchor", function (d) {
              return d.children ? "end" : "start";
            })
            .text(function (d) {
              return d.name;
            });
      });

      function dblclick(d) {
        d3.select(this).style('fill', '#f00')
        var a = d3.select(this)
        var b = this.children
        var c = 1
        for (child in this.children) {
          d3.select(this.children).style('fill', '#f00')
        }
      }

      d3.select(self.frameElement).style("height", height + "px");
    }
  }

  addComment(){
    console.log("dodaje komcia sdsadsfsdgsrgrg");
    let data ={
      comment: this.comment,
      fileName: this.fileDetails.title
    };
    console.log(data);
    this.usersService.addCommentToFile(data).then(function successCallback(response, status, headers, config) {
      this.state.go('logged.fileDetails',this.fileDetails);
    }.bind(this));

  }
  addScore(score){
    let data ={
      score: score,
      fileName: this.fileDetails.title
    };
    this.usersService.addOpinionToFile(data).then(function successCallback(response, status, headers, config) {
      this.state.go('logged.fileDetails',this.fileDetails);
    }.bind(this));
  }
  subscribe(){
    let addData = {
    email: this.usersService.userData.email,
    title: this.fileDetails.title
  };
    this.usersService.addFileToSubs(addData).then(function successCallback(response, status, headers, config) {
      console.log("Dupa234235243543t542524w");
    }.bind(this));
  }
  
  delSubs(){
    let delData = {
      email: this.usersService.userData.email,
      title: this.fileDetails.title
    };
    this.usersService.deleteFileFromSubs(delData).then(function successCallback(response, status, headers, config) {
      console.log("Dupahgjy7t7ti7i6i24w");
    }.bind(this));
  }




  //do cwiczenia
}


