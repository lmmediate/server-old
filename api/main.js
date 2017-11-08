var express = require('express');

var app = express();

app.use(express.static('../../web-app'));


var dataPath = '../crawler/out/sales'; // File extension is optional

app.get('/api/sales', function(req, res) {
  console.log(req.query);

  var data = require(dataPath);
  if(req.query.name) {
      data = data.filter(function(value) {
      return value.name.toLowerCase().indexOf(req.query.name.toLowerCase()) !== -1;
    });
  } 

  res.json(data);
});

app.get('/api/sales/categories', function (req, res) {
  var data = require(dataPath);
  data = data.map(function(value, index, arr) { 
    return value.category;
  }).filter(function (value, index, arr) { 
    return arr.indexOf(value) === index;
  });
  
  res.json(data);
});

var port = 8080;
app.listen(port, function() {
  console.log('Listening on port ' + port);
});
