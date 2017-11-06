var express = require('express');

var app = express();

app.use(express.static('../../web-app'));

var port = 4000;
app.listen(port, function() {
  console.log('Listening on port ' + port);
});
