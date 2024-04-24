document.getElementById("search_flight_by_fno").addEventListener("input", function (e) {
    let flightNo = document.getElementById("fname").value;

    if(flightNo.value === "") {
        console.log("empty input");
        document.getElementById('showDetails').style.display = 'none';
        document.getElementById('errorMessage').style.display = 'block';
        return;
    }

    $.ajax({
        type: 'GET',
        url: '/flightDetails', // Endpoint provided by your Spring Boot controller
        data: {flightNo: flightNo},
        success: function (response) {
            if (response != null && Object.keys(response).length>0) {
                // displayFlightDetails(response);
                console.log("in the data field");
                document.getElementById('flight_name').value = response.flightName;
                document.getElementById('flight_no').value = response.flightNo
                document.getElementById('from_city1').value = response.fromCity;
                document.getElementById('to_city1').value = response.toCity;
                document.getElementById('arrival_time').value = response.flightArrivalTime;
                document.getElementById('date_of_flight').value = response.dateOfFlight;

                // Show edit form
                document.getElementById('showDetails').style.display = 'block';

                // Hide error message
                document.getElementById('errorMessage').style.display = 'none';
            } else {
                console.log("in the error field");
                document.getElementById('showDetails').style.display = 'none';
                document.getElementById('errorMessage').style.display = 'block';
            }
        },
        error: function() {
            console.log("Error occurred while fetching data.");

            // Show error message
            document.getElementById('showDetails').style.display = 'none';
            document.getElementById('errorMessage').style.display = 'block';
        }
    });
})


