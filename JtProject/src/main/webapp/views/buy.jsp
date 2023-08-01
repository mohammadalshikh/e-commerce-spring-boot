<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>BestFood</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Pacifico&display=swap" rel="stylesheet">
    <style>

        body {
            font-family: 'Roboto', sans-serif;
            background-color: #F8F9FA;
            display: flex;
            flex-direction: column;
        }

        html, body {
            height: 100%;
        }

        .bg-image-wrapper {
            background-image: url('../bg.jpg'); /* Set the background image */
            background-size: cover;
            background-repeat: no-repeat;
            background-position: center top;
        }

        .navbar {
            background-color: transparent;
            font-weight: 500;
            font-size: 17px;
        }

        .navbar-brand {
            font-family: 'Pacifico', cursive;
            font-size: 28px;
            color: #fff;
        }

        .navbar-brand:hover {
            font-family: 'Pacifico', cursive;
            font-size: 28px;
            color: #e74c3c;
        }

        .navbar-nav .nav-link {
            color: #fff;
            transition: 0.5s ease;
        }

        .navbar-nav .nav-link:hover {
            color: #e74c3c;
            font-weight: bold;
        }

        .container {
            display: flex;
            justify-content: space-between; /* Arrange the content with space between them */
            flex-wrap: wrap; /* Allow the content to wrap on smaller screens */
        }

        .left-content {
            flex: 1; /* Allow the left-content to occupy available space */
            max-width: 300px; /* Set the maximum width of the left-content */
            padding: 10px;
        }

        .form-container {
            flex: 1; /* Allow the form-container to occupy available space */
            max-width: 500px; /* Set the maximum width of the form-container */
            padding: 20px;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            margin-left: 200px; /* Increase this value to push the form further to the right */
        }

        .footer {
            background-color: #292929;
            color: #fff;
            text-align: center;
            padding: 15px;
            font-family: 'Segoe UI', sans-serif;
            font-size: 14px;
        }

        .footer a {
            color: #fff;
            font-weight: bold;
            text-decoration: none;
            margin: 5px;
        }

        .footer a:hover {
            color: #e74c3c;
        }

    </style>
</head>
<body>
<div class="bg-image-wrapper">
    <nav class="navbar navbar-expand-lg">
        <div class="container">
            <a class="navbar-brand" href="/index">
                BestFood
            </a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                    aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="/index">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/shop">Shop</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/cart">Cart</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/profileDisplay">Profile</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/">Logout</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</div>
<br> <br>
<div class="container d-flex justify-content-center">
    <div class="left-content">
        <H1 style="white-space: nowrap">Order Summary</H1>
        <br>
        <H4>Items: $</H4>
        <br>
        <H4 style="white-space: nowrap">Shipping & Handling: FREE</H4>
        <br>
        <H4>Total before tax: $</H4>
        <br>
        <H4>-------------------</H4>
        <br> <br>
        <H4>Order Total: $</H4>
        <br>
        <form action="/applyCoupon" method="post">
            <label>Apply coupons</label>
            <input type="number" class="form-control" id="applyCoupon">
            <br>
            <button type="submit" class="btn btn-primary">Apply</button>
        </form>
    </div>
    <div class="form-container">
        <form id="payment-form" action="/buyCart" method="post">
            <div class="form-group">
                <label for="first-name">First Name</label>
                <input type="text" class="form-control" id="first-name" required>
            </div>
            <div class="form-group">
                <label for="last-name">Last Name</label>
                <input type="text" class="form-control" id="last-name" required>
            </div>
            <div class="form-group">
                <label for="card-number">Card Number</label>
                <input type="text" class="form-control" id="card-number" required>
            </div>
            <div class="form-row">
                <div class="form-group col-md-6">
                    <label for="expiry-month">Card Expiry Date (MM/YY)</label>
                    <input type="text" class="form-control" id="expiry-month" required>
                </div>
                <div class="form-group">
                    <label for="cvv">CVV</label>
                    <input type="password" class="form-control" id="cvv" required maxlength="3">
                </div>
            </div>
            <div class="form-group">
                <label for="street-address">Street Address</label>
                <input type="text" class="form-control" id="street-address" required>
            </div>
            <div class="form-row">
                <div class="form-group col-md-6">
                    <label for="city">City</label>
                    <input type="text" class="form-control" id="city" required>
                </div>
                <div class="form-group col-md-6">
                    <label for="postal-code">Postal Code</label>
                    <input type="text" class="form-control" id="postal-code" required maxlength="6">
                </div>
            </div>
            <div class="form-group">
                <label for="email">Email Address</label>
                <input type="email" class="form-control" id="email" required>
            </div>
            <button type="submit" class="btn btn-primary">Submit Payment</button>
        </form>
    </div>
</div>
<br> <br>
<footer class="footer">
    <p>&copy; 2023 BestFood</p>
    <div>
        <a href="/contact">Contact Us</a>
    </div>
</footer>

<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
<script>
    // Validate the postal code based on the specified criteria
    function validatePostalCode(postalCode) {
        const postalCodeRegex = /^[A-Za-z]\d[A-Za-z]\d[A-Za-z]\d$/;
        return postalCodeRegex.test(postalCode);
    }

    $(document).ready(function () {
        function formatPostalCode(postalCode) {
            return postalCode.toUpperCase().replace(/\s/g, '');
        }

        function getCursorPosition(input) {
            if ('selectionStart' in input) {
                return input.selectionStart;
            } else if (document.selection) {
                input.focus();
                var sel = document.selection.createRange();
                var selLen = document.selection.createRange().text.length;
                sel.moveStart('character', -input.value.length);
                return sel.text.length - selLen;
            }
            return 0;
        }

        function showPostalCodeValidationMessage(isValid) {
            const postalCodeInput = $("#postal-code");
            const validationMessage = $("#postal-code-validation");

            if (isValid) {
                postalCodeInput.removeClass("is-invalid");
                validationMessage.hide();
            } else {
                postalCodeInput.addClass("is-invalid");
                validationMessage.show();
            }
        }

        $("#postal-code").on('input', function (event) {
            const postalCodeInput = $(this);
            const formattedValue = formatPostalCode(postalCodeInput.val());
            const cursorPosition = getCursorPosition(this);

            postalCodeInput.val(formattedValue);

            // Adjust cursor position to allow smooth editing
            const newPosition = cursorPosition + (formattedValue.length - postalCodeInput.val().length);
            postalCodeInput[0].setSelectionRange(newPosition, newPosition);

            // Validate postal code
            const isValidPostalCode = validatePostalCode(formattedValue);
            showPostalCodeValidationMessage(isValidPostalCode);
        });

        $("#payment-form").submit(function (event) {
            event.preventDefault();
            const firstName = $("#first-name").val();
            const lastName = $("#last-name").val();
            const cardNumber = $("#card-number").val();
            const expiryMonth = $("#expiry-month").val();
            const cvv = $("#cvv").val();
            const streetAddress = $("#street-address").val();
            const city = $("#city").val();
            const postalCode = $("#postal-code").val().replace(/\s/g, ''); // Remove spaces
            const email = $("#email").val();

            // Validate postal code
            const isValidPostalCode = validatePostalCode(postalCode);
            showPostalCodeValidationMessage(isValidPostalCode);

            if (!isValidPostalCode) {
                return;
            }

            // You can add further validation for other fields if required

            // Now you can proceed with form submission or payment processing
            // For demonstration purposes, we'll just log the form data
            const formData = {
                firstName,
                lastName,
                cardNumber,
                expiryMonth,
                cvv,
                streetAddress,
                city,
                postalCode,
                email
            };
        });
    });
</script>
</body>
</html>
