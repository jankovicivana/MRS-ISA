<template>
  <div>
    <fishing-instructor-navbar></fishing-instructor-navbar>
    <section class="hero is-fullheight-with-navbar main_homepage">
      <div class="hero-body">
        <div class="container has-text-centered ml-4 pl-4">
          <h1 class="title">Welcome, fishing instructor!</h1>
          <p class="text">Add new adventures now.</p>
        </div>
      </div>
    </section>

    <div id="adventures" class="block p-6 mt-1 mb-6" style="background: aliceblue">
      <div class="container ml-5 content align-items-center">
        <h2>Your adventures</h2>

        <br />
        <div class="columns">
          <div class="column" v-for="a in adventures">
            <div class="card col-3" v-on:click="open(a.id)">
              <div class="card-image">
                <carousel :per-page="1" :navigationEnabled="false" :mouse-drag="false" :autoplay="true" :paginationEnabled="false" v-bind:loop="true" v-bind:speed="3000" >
                  <slide v-for="i in a.images">
                    <img class="d-block w-100" :src="require('../assets/images/'+i.path)" alt="First slide" style="height: 250px">
                  </slide>
                </carousel>
              </div>
              <div class="p-3">
                <h4>{{ a.name }}</h4>
                <p><font-awesome-icon icon="fa-map-marker"/> {{ a.address.street }}</p>
                <p>{{ a.address.city}}</p>
                <p><b>$<span>{{ a.price }}</span></b></p>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="has-text-centered mt-6">
        <a href="#/adventures" class="button view_button is-primary">View more</a>
      </div>
    </div>



    </div>
</template>

<script>
import axios from "axios";
import FishingInstructorNavbar from "./header/FishingInstructorNavbar";

export default {
  name: "FishingInstructorHomepage",
  components: {FishingInstructorNavbar},
  data: function(){
    return{
      adventures: ''
    }
  },
  mounted: function (){
    axios
      .get(process.env.VUE_APP_SERVER_PORT+"/api/adventures/allByOwner", {headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
      .then(response => (this.adventures = response.data))
  },
  methods:{
    open:function (id){
      this.$router.push({name:"AdventureProfile",params:{id:id}});
    }
  }
}
</script>

<style scoped>
.main_homepage {
  background-attachment: fixed;
  background-image: url('../assets/images/instructorHomepage2.jpg');
  background-position-y: 0;
  background-repeat: no-repeat;
  background-size: cover;
  opacity: 1;
  top: 0;
  transition: opacity 0.3s linear 0s;
  width: 100%;
  box-sizing: border-box;

}


* {
  margin: 0;
  box-sizing: border-box;
}

.card_image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.card-image {
  height: 250px;
}

.card {
  cursor: pointer;
  width: 300px;
  transition: transform .2s;
}

.card:hover{
  transform: scale(1.08);
}

a{
  text-decoration: none;
  color: white;
}

h1{
  color: white;
  font-size: xxx-large;
  font-weight: bold;
}

.text{
  color: white;
  font-size: xx-large;
}

.view_button{
  background-color: #4AAE9B;
  color: white;
}

.view_button:hover{
  background-color: #2e6b6b;
}

h2 {
  text-align: center;
}

</style>
