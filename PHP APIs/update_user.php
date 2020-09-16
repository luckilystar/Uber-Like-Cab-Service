<?php require ('constants.php');
$con = mysqli_connect($dbServer, $dbUsername, $dbPassword, $dbName);
$user_name = $_POST['user_name'];
$phone = $_POST['phone'];
$email = $_POST['email'];
$user_id = $_POST['user_id'];
$password = mysqli_escape_string($con, $_POST['password']);

if(!$name || !$phone || !$email || !$password){
  echo json_encode(array('message'=>'required field is empty.'));
}else{
$query = mysqli_query($con, "UPDATE users SET user_name='$user_name', phone='$phone', password='$password' WHERE user_id = '$user_id'");
if($query){
    echo json_encode(array('message'=>'student data successfully updated.'));
  }else{
    echo json_encode(array('message'=>'student data failed to update.'));
  }
}
?>