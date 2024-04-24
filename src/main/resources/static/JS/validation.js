console.log("in the js file");


document.getElementById("fullname").addEventListener("input", function (event) {
    document.getElementById("fullname-error").innerText = "";
});

document.getElementById("username").addEventListener("input", function (event) {
    document.getElementById("username-error").innerText = "";
});

document.getElementById("city").addEventListener("input", function (event) {
    document.getElementById("city-error").innerText = "";
});

document.getElementById("pincode").addEventListener("input", function (event) {
    document.getElementById("pincode-error").innerText = "";
});


document.getElementById("email_address").addEventListener("input", function (event) {
    let emailaddress = this.value.trim();
    let emailerrormessage = document.getElementById("email_address-error");

    if (!validateemail(emailaddress)) {
        console.log("invalid email address");
        emailerrormessage.innerText = "* Email is in incorrect format";
        event.preventDefault();
    } else {
        emailerrormessage.innerText = ""; // Clear error message if validation passes
    }
});

document.getElementById("phno").addEventListener("input", function (event) {
    let phoneNumber = this.value.trim();
    let phoneNumberErrorMessage = document.getElementById("phno-error");

    if (!validatePhoneNumber(phoneNumber)) {
        phoneNumberErrorMessage.innerText = "* Phone number must be in valid format";
        event.preventDefault();
    } else {
        phoneNumberErrorMessage.innerText = ""; // Clear error message if validation passes
    }
});

document.getElementById("password").addEventListener("input", function (event) {
    let password = this.value.trim();
    let passwordErrorMessage = document.getElementById("password-error");
    console.log("in password event");
    if (!validatepassword(password)) {
        console.log("validating password");
        passwordErrorMessage.innerText = "* Password must be in valid format";
        event.preventDefault();
    } else {
        passwordErrorMessage.innerText = "";
    }
});


function validateemail(email) {
    let emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}


function validateReg(event) {
    console.log("in the validation reg file");
    const fields = {
        fullname: document.getElementById('fullname'),
        username: document.getElementById('username'),
        password: document.getElementById('password'),
        phno: document.getElementById('phno'),
        email_address: document.getElementById('email_address'),
        state: document.getElementById('state'),
        city: document.getElementById('city'),
        pinCode: document.getElementById('pincode'),
    };

    //validate required fields
    for (const field in fields) {

        const fieldValue = fields[field].value.trim();
        if (fieldValue === "") {
            console.log("in side the null validation");
            displayerror(document.getElementById(`${field}-error`), "* This field is required");
            event.preventDefault();
            return false;
        }
    }

    if (!validateemail(fields.email.value)) {
        displayerror(document.getElementById("email-error"), "* Email is in incorrect format");
        event.preventDefault();
        return false;
    }

    const password = document.getElementById("password").value;
    if (password.length <= 8 || password.length >= 16) {
        console.log("in password if block");
        displayerror(document.getElementById("password-error"), "* Password must be between 8 and 16 characters");
        event.preventDefault();
        return false;
    }

    if (!validatepassword(password)) {
        displayerror(document.getElementById("password-error"), "* Password must be in valid format");
        event.preventDefault();
        return false;
    }

    if (!validatePhoneNumber(phno)) {
        displayerror(document.getElementById("phno-error"), "* Phone number must be in valid format");
        event.preventDefault();
        return false;
    }

}

function displayerror(element, errormessage) {
    element.innerText = errormessage;
}


function validatepassword(password) {
    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,16}$/;
    return passwordRegex.test(password);
}

function validatePhoneNumber(phoneNumber) {
    const PhoneNumberRegex = /^\d{10}$/;
    return PhoneNumberRegex.test(phoneNumber);

}




