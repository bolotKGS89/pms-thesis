import express from 'express';
const router = express.Router();

const apiForm = (req, res) => {
    res.render('payment/form');
};

const playerForm = (req, res) => {
    res.render('players/playerForm');
};


// Define routes within the controller
router.get("/api/form", apiForm);

router.get("/api/player-form", playerForm);

export default router;