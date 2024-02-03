function createPlayer() {
    const form = document.getElementById('playerForm');
    const formData = new FormData(form);
    const apiUrl = `http://localhost:3000/api/player/create`;

    // Convert form data to JSON
    const jsonData = {};
    formData.forEach((value, key) => {
        jsonData[key] = value;
    });
        
    // Use fetch to send a POST request to the API endpoint
    fetch(apiUrl, {
        method: 'POST',
        headers: {
        'Content-Type': 'application/json',
        },
        body: JSON.stringify(jsonData),
    })
    .then(response => response.json())
    .then(data => {
        alert('Response: ' + JSON.stringify(data));
        location.reload();
    })
    .catch(error => {
        alert('Error:' + error.message);
    });
}

function retrievePlayer() {
    const form = document.getElementById('playerForm');
    const formData = new FormData(form);
    const playerId = Object.fromEntries(formData.entries())['playerId'];
    const apiUrl = `http://localhost:3000/api/player/retrieve/${playerId}`;
    // Use fetch to send a GET request to the API endpoint
    fetch(apiUrl, {
        method: 'GET',
        headers: {
        'Content-Type': 'application/json',
        },
    })
    .then(response => response.text())
    .then(data => {
      alert('Response: ' + data);
      location.reload();
    })
    .catch(error => {
      alert('Error:' + error.message);
    });
}

function updatePlayer() {
    const form = document.getElementById('playerForm');
    const formData = new FormData(form);
    const playerId = Object.fromEntries(formData.entries())['playerId'];
    const apiUrl = `http://localhost:3000/api/player/update/${playerId}`;

     // Convert form data to JSON
     const jsonData = {};
     formData.forEach((value, key) => {
         jsonData[key] = value;
     });

    // Use fetch to send a GET request to the API endpoint
    fetch(apiUrl, {
        method: 'PUT',
        headers: {
        'Content-Type': 'application/json',
        },
        body: JSON.stringify(jsonData),
    })
    .then(response => response.text())
    .then(data => {
      alert('Response: ' + data);
      location.reload();
    })
    .catch(error => {
      alert('Error:' + error.message);
    });
}

function deletePlayer() {
    const form = document.getElementById('playerForm');
    const formData = new FormData(form);
    const playerId = Object.fromEntries(formData.entries())['playerId'];
    const apiUrl = `http://localhost:3000/api/player/delete/${playerId}`;
    // Use fetch to send a GET request to the API endpoint
    fetch(apiUrl, {
        method: 'DELETE',
        headers: {
        'Content-Type': 'application/json',
        },
    })
    .then(response => response.text())
    .then(data => {
      alert('Response: ' + data);
      location.reload();
    })
    .catch(error => {
      alert('Error:' + error.message);
    });
}