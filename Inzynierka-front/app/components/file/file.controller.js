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
    this.flagInteractive = false;

    this.flagTmp = true;
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

    var svg = d3.select("body").append("svg")
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
  $onInit() {
    if (this.fileDetails.type != "image" && this.fileDetails.type != "chart"){
      this.flagType = true;

    }

    if (this.fileDetails.type == "json"){
      console.log("interactive");
      //TUTAJ JEST TEN TYP NA INTERAKTYWNE
      this.flagInteractive = true;
    }

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


