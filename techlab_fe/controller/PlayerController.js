// controllers/visaController.js
import express from 'express';
import config from '../config.js';
import axios from 'axios';
import { v4 } from 'uuid';
import log4js from 'log4js';
import request from 'superagent';

const router = express.Router();
log4js.configure({
  appenders: {
    logstash: {
      type: 'log4js-logstash-tcp',
      host: 'logstash',
      port: 5000
    },
  },
  categories: {
    default: {
      appenders: ['logstash'],
      level: 'info',
    },
  },
});
// Get a logger instance
const logger = log4js.getLogger();

// Set timeout to 2000 milliseconds (2 seconds)
const timeoutInMilliseconds = 2000;

const createPlayer = (req, res) => {
    
    const requestHeaders = {
      'X-Request-ID' : v4().toString(),
      'Service-Name' : 'fe'
    }

    logger.info(`Sending message to ugo (request_id: ${requestHeaders['X-Request-ID']})`)
    request
    .post(`${config.ugoUrl}`)
    .send(req.body)
    .set(requestHeaders)
    .timeout({ response: timeoutInMilliseconds })
    .then(response => {
      logger.info(`Receiving answer from ${response.body['serviceName']} (request_id: ${response.body['requestId']})`);
      res.send(response.body);
    })
    .catch(error => {
      console.log(error.response.body['serviceName'])
      logger.error(`Error response (code: 500) received from ${error.response.body['serviceName']} (request_id: ${error.response.body['requestId']})`);
      res.status(500).send('Error');
      // if (error.timeout) {
      //   console.log('timeout')
      //   console.log(error.response)
      //   // logger.error(`Failed to contact ${requestHeaders['Service-Name']} (request_id: ${requestHeaders['X-Request-ID']}). Root cause: ${error.message}`);
      //   res.status(500).send('Request timed out');
      // } else if (error.response) {
      //   // logger.error(`Error response (code: ${error.response.status}) received from techlab_ugo (request_id: ${requestHeaders['X-Request-ID']})`);
      //   res.status(error.response.status).send(error.response.body);
      // } else {
      //   console.log('timeout')
      //   console.log(error.response)
      //   // logger.error(`Failed to contact ${requestHeaders['Service-Name']} (request_id: ${requestHeaders['X-Request-ID']}). Root cause: ${error.message}`);
      //   res.status(500).send('Request timed out');
      // }
    });
  }
  
const retrievePlayer = (req, res) => {
      // Use req.params.id to access the id parameter
    const { id } = req.params;

    const requestHeaders = {
      'X-Request-ID' : v4().toString(),
      'Service-Name' : 'techlab_fe'
    }

    logger.info(`Sending message to techlab_ugo (request_id: [${requestHeaders['X-Request-ID']}])`)
    axios.get(`${config.ugoUrl}/${id}`,{
      headers: requestHeaders
    })
    .then(response => {
      logger.info(`Receiving answer from techlab_ugo (request_id: [${requestHeaders['X-Request-ID']}])`)
      res.send(response.data);
    })
    .catch(error => {
      console.log(error.code)
      if (error.code === 'ERR_BAD_RESPONSE') {
        if(error.response) {
          logger.error(`Failing to contact ${error.response.data['serviceName']} (request_id: [${requestHeaders['X-Request-ID']}]). Root cause: ${error.response.data['message']}`)
          res.send(error.response.data);
        }    
      } else {
        if(error.response) {
          logger.error(`Error response (code: 500) received from ${error.response.data['serviceName']} (request_id: [${requestHeaders['X-Request-ID']}])`)
          res.send(error.response.data);
        }
      }     
    });
}

const updatePlayer = (req, res) => {
  // Use req.params.id to access the id parameter
  const { id } = req.params;

  const requestHeaders = {
    'X-Request-ID' : v4().toString(),
    'Service-Name' : 'techlab_fe'
  }

  logger.info(`Sending message to techlab_ugo (request_id: [${requestHeaders['X-Request-ID']}])`)
  axios.put(`${config.ugoUrl}/${id}`, req.body, {
    headers: requestHeaders
  })
  .then(response => {
    logger.info(`Receiving answer from techlab_ugo (request_id: [${requestHeaders['X-Request-ID']}])`)
    res.send(response.data);
  })
  .catch(error => {
    console.log(error.code)
    if (error.code === 'ERR_BAD_RESPONSE') {
      if(error.response) {
        logger.error(`Error response (code: 500) received from ${error.response.data['serviceName']} (request_id: [${requestHeaders['X-Request-ID']}])`)
        res.send(error.response.data);
      }    
    } else {
      if(error.response) {
        logger.error(`Failing to contact ${error.response.data['serviceName']} (request_id: [${requestHeaders['X-Request-ID']}]). Root cause: ${error.response.data['message']}`)
        res.send(error.response.data);
      }
    }     
  });
}

const deletePlayer = (req, res) => {
  // Use req.params.id to access the id parameter
  const { id } = req.params;

  const requestHeaders = {
    'X-Request-ID' : v4().toString(),
    'Service-Name' : 'techlab_fe'
  }

  logger.info(`Sending message to techlab_ugo (request_id: [${requestHeaders['X-Request-ID']}])`)
  axios.delete(`${config.ugoUrl}/${id}`, {
    headers: requestHeaders
  })
  .then(response => {
    logger.info(`Receiving answer from techlab_ugo (request_id: [${requestHeaders['X-Request-ID']}])`)
    res.send(response.data);
  })
  .catch(error => {
    console.log(error.code)
    if (error.code === 'ERR_BAD_RESPONSE') {
      if(error.response) {
        logger.error(`Failing to contact ${error.response.data['serviceName']} (request_id: [${requestHeaders['X-Request-ID']}]). Root cause: ${error.response.data['message']}`)
        res.send(error.response.data);
      }    
    } else {
      if(error.response) {
        logger.error(`Error response (code: 500) received from ${error.response.data['serviceName']} (request_id: [${requestHeaders['X-Request-ID']}])`)
        res.send(error.response.data);
      }
    }     
  });
}

// Define routes within the controller
router.post("/api/player/create", createPlayer);

router.get("/api/player/retrieve/:id", retrievePlayer);

router.put("/api/player/update/:id", updatePlayer);

router.delete("/api/player/delete/:id", deletePlayer);

export default router;