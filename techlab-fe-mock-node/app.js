import visaController from './controller/VisaController.js';
import amazonController from './controller/AmazonController.js';
import paypalController from './controller/PayPalController.js';
import formController from './controller/FormController.js';
import playerController from './controller/PlayerController.js';
import config from './config.js';
import express from 'express';
import { getRootDirectory } from './utils.js';

const app = express();
const port = config.server.port;

// the root directory using the utility function
const rootDirectory = getRootDirectory();


app.set('view engine', 'pug');
app.set('views', `${rootDirectory}/views`);
// Serve static files from the 'views' directory
app.use(express.static('views', { extensions: ['html', 'js'] }));
app.use(express.json());

app.use(visaController);
app.use(paypalController);
app.use(amazonController);
app.use(formController);
app.use(playerController);

app.listen(port, () => {
    console.log(`Mock Front-End Microservice is running at http://localhost:${port}`);
});