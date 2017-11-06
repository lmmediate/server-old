var express = require('express');
var fs = require('fs');

var app = express();

app.get('/', function(req, res) {
  console.log('GET');
  res.status(200).json({ name: 'Maxim' });
});

var port = 4000;
app.listen(port, function() {
  console.log('Listening on port ' + port);
})
