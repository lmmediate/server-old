var express = require('express');

var app = express();

app.use(express.static('../../web-app'));



var data = require('../crawler/out/sales'); // File extension is optional

app.get('/api/sales', function(req, res) {
  res.json(data);
});

app.get('/api/sales/categories', function (req, res) {
  var cats = data.map(function(value, index, arr) { 
    return value.category;
  }).filter(function (value, index, arr) { 
    return arr.indexOf(value) === index;
  });
  res.json(cats);
});

var port = 8080;
app.listen(port, function() {
  console.log('Listening on port ' + port);
});
