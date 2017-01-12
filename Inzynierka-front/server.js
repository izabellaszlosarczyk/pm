var path = require('path');
var express = require('express');
var app = express();
var PORT = process.env.PORT || 8080
var IP = process.env.IP
console.log("server")
process.env.NODE_ENV = 'development'
// using webpack-dev-server and middleware in development environment
if(process.env.NODE_ENV !== 'production') {
  var webpackDevMiddleware = require('webpack-dev-middleware');
  var webpackHotMiddleware = require('webpack-hot-middleware');
  var webpack = require('webpack');
  var config = require('./webpack.config');
  var compiler = webpack(config);
  
  app.use(webpackDevMiddleware(compiler, { noInfo: true, publicPath: config.output.publicPath }));
  app.use(webpackHotMiddleware(compiler));
}

app.use(express.static(path.join(__dirname, 'app')));

app.get('/', function(request, response) {
  response.sendFile(__dirname + '/app/index.html')
});

app.listen(PORT,IP, function(error) {
  if (error) {
    console.error(error);
  } else {
    console.info("==> 🌎  Listening on port. Visit http://%s:%s/ in your browser.", IP, PORT);
  }
});
