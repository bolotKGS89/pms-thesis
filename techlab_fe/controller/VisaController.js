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

const createPayment = (req, res) => {

  const requestHeaders = {
    'X-Request-ID' : v4().toString(),
    'Service-Name' : 'fe'
  }

  logger.info(`Sending message to ewallet (request_id: ${requestHeaders['X-Request-ID']})`)
  request
  .post(`${config.ewalletUrl}/create`)
  .send(req.body)
  .set(requestHeaders)
  .timeout({ response: timeoutInMilliseconds })
  .then(response => {
    logger.info(`Receiving answer from ${response.body['serviceName']} (request_id: ${response.body['requestId']})`);
    res.send(response.body);
  })
  .catch(error => {
    logger.error(`Error response (code: 500) received from ${error.response.body['serviceName']} (request_id: ${error.response.body['requestId']})`);
    res.status(500).send('Error');
  });
}

const retrievePayment = (req, res) => {
  // Use req.params.id to access the id parameter
  const { id } = req.params;
  
  logger.info(`Sending message to techlab_ewallet (request_id: [${requestHeaders['X-Request-ID']}])`)
  axios.get(`${config.ewalletUrl}/retrieve/visa/${id}`, {
    headers: requestHeaders
  })
  .then(response => {
    logger.info(`Receiving answer from techlab_ewallet (request_id: [${requestHeaders['X-Request-ID']}])`)
    res.send(response.data);
  })
  .catch(error => {
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

const confirmPayment = (req, res) => {
  // Use req.params.id to access the id parameter
  const { id } = req.params;
  
  logger.info(`Sending message to techlab_ewallet (request_id: [${requestHeaders['X-Request-ID']}])`)
  axios.get(`${config.ewalletUrl}/confirm/visa/${id}`, {
    headers: requestHeaders
  })
  .then(response => {
    logger.info(`Receiving answer from techlab_ewallet (request_id: [${requestHeaders['X-Request-ID']}])`)
    res.send(response.data);
  })
  .catch(error => {
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

const refundPayment = (req, res) => {

  logger.info(`Sending message to techlab_ewallet (request_id: [${requestHeaders['X-Request-ID']}])`)
  axios.post(`${config.ewalletUrl}/refund`, req.body, {
    headers: requestHeaders
  })
  .then(response => {
    logger.info(`Receiving answer from techlab_ewallet (request_id: [${requestHeaders['X-Request-ID']}])`)
    res.send(response.data);
  })
  .catch(error => {
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

const capturePayment = (req, res) => {
  // Use req.params.id to access the id parameter
  const { id } = req.params;
  
  logger.info(`Sending message to techlab_ewallet (request_id: [${requestHeaders['X-Request-ID']}])`)
  axios.get(`${config.ewalletUrl}/capture/visa/${id}`, {
    headers: requestHeaders
  })
  .then(response => {
    logger.info(`Receiving answer from techlab_ewallet (request_id: [${requestHeaders['X-Request-ID']}])`)
    res.send(response.data);
  })
  .catch(error => {
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
router.post("/api/visa/create", createPayment);

router.get("/api/visa/retrieve/:id", retrievePayment);

router.get("/api/visa/confirm/:id", confirmPayment);

router.post("/api/visa/refund", refundPayment);

router.get("/api/visa/capture/:id", capturePayment);



export default router;