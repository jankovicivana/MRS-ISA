<template>
  <div id="app">
    <main>
      <MyNavbar></MyNavbar>
      <router-view></router-view>
    </main>
  </div>
</template>

<script>
import MyNavbar from "./components/MyNavbar";
export default {
  name: 'app',
  components: {MyNavbar}
}
</script>

<style>
.form-group a i {
  font-family: FontAwesome, serif;
  margin: 0 auto;
  font-size: 5rem;
  font-style: normal;
}

.input_add {
  border: white;
}

.main_photo{
  border-radius: 5%;
}

.profile_main{
  background-image: url('assets/images/back2.jpg');
  background-size: 100% 100%;
}

.profile_boat{
  background-image: url('assets/images/backboat.jpg');
  background-size: 100% 100%;
}

.add_boat{
  background-image: url('assets/images/boat4.jpg');
  background-size: 100% 100%;
}

.side_photo img{
  border-radius: 5%;
}

.cottage_profile{
  background: #ecd9c6;
  border: 4px solid white;
  border-radius: 2%;
}

.adventure_profile{
  background: white;
  border: 4px solid white;
  border-radius: 2%;
}
.boat_profile{
  background: aliceblue;
  border: 4px solid white;
  border-radius: 2%;
}

.number_info p{
  font-size: 20px;
}

.number_info i{
  color: black;
}

.services{
  font-size: 18px;
}

.services i{
  color: black;
  font-size: 15px;
}

.rules{
  font-size: 18px;
}

.rules i{
  color: #00b3b3;
  font-size: 8px;
}

.res_button{
  margin-left: 75%;
  padding-top: 50px;
  padding-bottom: 20px;
}

.res_button button{
  color: white;
  background: #2e6b6b;
}

#quick_heading{
  font-size: 25px;
  color:#c91d1d;
  font-weight: bold;
}

.quick_res{
  border-radius: 5%;
  background: white;
  font-size: 20px;
  border: 3px solid #c91d1d;
  width: 32%;
}

.res_date{
  display: inline-block;
  font-weight: bold
}

.discount{
  background: #c91d1d;
  display: inline-block;
  margin-left: 15%;
  color: white;
  border-radius: 50%;
  padding: 5px
}

.quick_res_btn{
  margin-left: 70%;
}

.quick_res_btn button{
  background: #c91d1d;
  color: white;
}

.before_price{
  color: #c91d1d;
}




.profile-head {
  transform: translateY(2rem)
}


.delete-btn{
  float: right;
  height: 30px;
  width: 150px;
  border: none;
  color: #fff;
  border-radius: 4px;
  background-color: cadetblue;
  cursor: pointer;
  text-transform: uppercase
}

.form-control{
  background-color: white;
}


.delete-btn:hover {
  background: darkcyan;
}

.delete-btn:focus {
  background: darkcyan;
  box-shadow: none
}

.delete-btn:active {
  background: darkcyan;
  box-shadow: none
}

.cover {
  background-color: #ecd9c6;
  background-repeat: no-repeat;
  background-attachment: fixed;
  background-size: cover;
}



.container #client_profile_container{
  height: 80vh;
  display: flex;
  justify-content: left;
  align-items: center;
  background-color: burlywood;
}



.inputs {
  display: flex;
  flex-direction: column;
  margin-bottom: 10px;
}



.inputs input {
  height: 40px;
  padding: 0px 10px;
  font-size: 17px;
  box-shadow: none;
  outline: none;
  background-color: white;
}
.inputs input[type="text"][readonly] {
  border: 2px solid rgba(0, 0, 0, 0);
  background-color: white;
}



.edit-button {
  box-shadow: none;
  border: none;
  background-color: cadetblue;
  cursor: pointer;
  text-transform: uppercase
}

.edit-button:hover {
  background: darkcyan;
}

.edit-button:focus {
  background: darkcyan;
  box-shadow: none
}

.edit-button:active {
  background: darkcyan;
  box-shadow: none
}

.labels {
  font-size: 11px
}

</style>
