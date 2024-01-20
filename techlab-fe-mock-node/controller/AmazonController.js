import express from 'express';
const router = express.Router();

const users = [
  { id: 1, name: 'User 1' },
  { id: 2, name: 'User 2' },
];

// Define routes within the controller
router.get("/api/amazon/users", (req, res) => {
  res.json(users);
});

export default router;