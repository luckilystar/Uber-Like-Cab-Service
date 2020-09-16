<?php require ('constants.php');
$user_id = mysqli_escape_string($con, $_POST['user_id']);
$name = mysqli_escape_string($con, $_POST['name']);
$phone = mysqli_escape_string($con, $_POST['phone']);
$email = mysqli_escape_string($con, $_POST['email']);
$password = mysqli_escape_string($con, $_POST['password']);
$con = mysqli_connect($dbServer, $dbUsername, $dbPassword, $dbName);
if (!$con)
{
    $response = array(
        "status" => "0",
        "data" => "Error Connecting to Database!"
    );
    die(json_encode($response));
}
$getUser = "SELECT user_name, phone, email, password FROM users WHERE user_id=$user_id";
$result = mysqli_query($con, $getUser);
if ($result)
{
    $r = mysqli_fetch_assoc($result);
    $response = array(
        "status" => "1",
        "data" => array(
            "user_name" => $r['user_name'],
            "phone" => $r['phone'],
            "email" => $r['email'],
            "password" => $r['password']
        )
    );
    echo (json_encode($response));
} ?>
