// Function to continuously check session attributes and reload the page if necessary
function checkSessionAttributes() {
    function fetchSessionData() {
        // Send an AJAX request to the server to fetch session data
        fetch('/getSessionData')
            .then(response => {
                if (response.ok) {
                    // Session attribute present, reload the page

                } else {
                    // Session attribute not present
                    window.location.reload();
                    console.log('Session attribute not present');
                }
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }

    // Call fetchSessionData initially
    fetchSessionData();

    // Set interval to continuously check session attributes
    setInterval(fetchSessionData, 5000); // Adjust the interval time (in milliseconds) as needed
}

// Call the function when the page loads
window.onload = checkSessionAttributes;
