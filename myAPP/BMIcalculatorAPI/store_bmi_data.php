<?php

//IF the user does not enter height or weight 
if(!isset($_GET["height"]) || !isset($_GET["weight"])){
    $feedback["bmi"] ="_";
    $feedback["information"] = "Enter your height and weight to calculate your BMI";
    
}
else{
    //store API data into variables
    $height = $_GET["height"];
    $weight = $_GET["weight"];

     // calculate bmi and round function for converting into two decimal places
     $bmi = round($weight/($height*$height),2);
     $feedback["bmi"] = round($bmi,2);

     //my bmi status

    if($bmi<18.5){

    //print results
        $feedback["information"] = "You are underweight";

    //store to the variables for database and for the app to the user
         $final_bmi =  $feedback["bmi"];
         $final_message = $feedback["information"];
        
     }
     else if($_bmi>=18.5 && $_bmi<=24.9){

        //print results
        $feedback["information"] = "Super Healthy weight";

        //store to the variables
         $final_bmi = $feedback["bmi"];
         $final_message = $feedback["information"];

     }
    else if($_bmi>=25 && $_bmi<=29.9){
        //print results
        $feedback["information"]= "You are Overweight";

        //store to the variables
         $final_bmi = $feedback["bmi"];
         $final_message = $feedback["information"];
        
    }
    else{
        //print results
        $feedback["information"] = "You are Obesity";

        //store to the variables
         $final_bmi = $feedback["bmi"];
         $final_message = $feedback["information"];
        
    
        
    }
    //Converting data into JSON format from a human redable information
    header("content-Type: application/json, charset = utf-8");
    //The array results
    echo json_encode($feedback);


    
     // start-connection
    $conn= mysqli_connect("localhost","root","pemmy627@","bmi_api");

    //Test connection
    if($conn){

        // insert api data into database
    
        $_sql=mysqli_query($conn, "insert into bmi_tbl(height,weight,bmi_data,status) values('$height','$weight','$final_bmi','$final_message')");

        //Notification
        $response = ["Notification" => "Data has been stored successfully"];
        echo "\n";
        echo json_encode($response);
    }

    else{
        echo "connection unsuccessfuly";
    }
    
}


?>