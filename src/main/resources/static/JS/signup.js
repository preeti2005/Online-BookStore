
window.onload = function () {
    validateForm();
};

function validateForm() {
    // Read input fields
    const email = document.getElementById("email");
    const uname = document.getElementById("username");
    const password = document.getElementById("password");
    const confirmPassword = document.getElementById("confirm-password"); // Correct ID
    const submitBtn = document.querySelector("button[type='submit']");

    // Validation logic
    const fields = [email,uname, password, confirmPassword];

    fields.forEach((field) => {
        field.addEventListener("input", () => {
            const allFilled = fields.every((f) => f && f.value.trim() !== "");
            submitBtn.disabled = !allFilled;
        });
    });
}

function handleSubmit(book) {
    // Read form elements
    const email = document.getElementById("email");
    const uname = document.getElementById("username");
    const password = document.getElementById("password");
    const confirmPassword = document.getElementById("confirm-password");

    // Initialize error variables
    let emailErr = "";
    let unameErr = "";
    let passwordErr = "";
    let confirmPasswordErr = "";

    // Validate username
    if (uname.value.length < 5 || uname.value.length > 20) {
        unameErr = "Name must be between 5 and 20 characters";
    } else if (!/^[A-Za-z]+( [A-Za-z]+)*$/.test(uname.value)) {
        unameErr = "Name must contain only alphabets";
    }

    // Validate password
    if (password.value.length < 8 || password.value.length > 12) {
        passwordErr = "Password must be between 8 and 12 characters";
    }

    // Validate confirm password
    if (confirmPassword.value !== password.value) {
        confirmPasswordErr = "Passwords do not match";
    }

    // Validate email
 if (!/^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/.test(email.value)) {
    emailErr = "Invalid email format";
}

    // Display errors
    document.getElementById("usernameErr").innerText = unameErr;
    document.getElementById("passwordErr").innerText = passwordErr;
    document.getElementById("confirmPasswordErr").innerText = confirmPasswordErr;
    document.getElementById("emailErr").innerText = emailErr;
    console.log(unameErr,passwordErr,confirmPasswordErr,emailErr);
    // Prevent form submission if errors exist
    if (unameErr || passwordErr || confirmPasswordErr || emailErr) {
        book.preventDefault();
        return false;
    }

    return true;
}
