var express = require('express');

var app = express();
app.use('/api', require('./routes/api'));
app.use(express.static('../../web-app'));

var port = 8080;
app.listen(port, function() {
  console.log('Listening on port ' + port);
});
