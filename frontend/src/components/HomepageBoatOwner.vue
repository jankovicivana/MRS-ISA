<template>
  <div>
    <BoatOwnerNavbar></BoatOwnerNavbar>
    <section class="hero is-fullheight-with-navbar main_homepage">
      <div class="hero-body">
        <div class="container has-text-centered ml-4 pl-4">
          <h1 class="title">Welcome boat owner!</h1>
          <p class="text">Add your perfect boat now.</p>
        </div>
      </div>
    </section>



    <div id="boats" class="block p-6 mt-1 mb-3" style="background: aliceblue">
      <div class="container ml-5 content align-items-center">
        <h2>Your boats</h2>
        <br />
        <div class="columns">
          <div class="column" v-for="b in boats.slice(0,4)">
            <div class="card col-3" v-on:click="open(b.id)">
              <div class="card-image">
                <carousel :per-page="1" :navigationEnabled="false" :mouse-drag="false" :autoplay="true" :paginationEnabled="false" v-bind:loop="true" v-bind:speed="3000" >
                  <slide v-for="i in b.images">
                    <img class="d-block w-100" :src="i" alt="First slide" style="height: 250px">
                  </slide>
                </carousel>
              </div>

              <div class="p-3">
                <h4>{{ b.name }}</h4>
                <p><font-awesome-icon icon="fa-map-marker"/> {{ b.address.street }}</p>
                <p>{{ b.address.city}}</p>
                <p><b>$<span>{{ b.price }}</span></b></p>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="has-text-centered mt-6">
        <a href="#/boats" class="button view_button is-primary">View more</a>
      </div>
    </div>



  </div>
</template>

<script>
import BoatOwnerNavbar from "./header/BoatOwnerNavbar";
import axios from "axios";
export default {
  name: "HomepageBoatOwner",
  components: {BoatOwnerNavbar},
  data: function(){
    return{
      boats: ''
    }
  },
  mounted: function (){
    axios
      .get("https://rental-app-6.herokuapp.com"+"/api/boats/allByOwner", {headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
      .then(response => {this.boats = response.data
        response.data.forEach(boat => {
          var images = []
          boat.images.forEach(image => {
            axios.get("https://rental-app-6.herokuapp.com"+"/api/images/getImage/"+image.path,{responseType:"blob"})
              .then(response => {
                images.push(URL.createObjectURL(response.data));
                boat.images = images;
              })
              .catch((error) =>{
                console.log(error);
              });
          });
        });
      })
  },
  methods:{
    open:function (id){
      this.$router.push({name:"BoatProfile",params:{id:id}});
    }
  }
}
</script>

<style scoped>
.main_homepage {
  background-attachment: fixed;
  background-image: url('../assets/images/backboat2.jpg');
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
