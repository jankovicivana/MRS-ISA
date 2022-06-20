<template>
  <div>
    <section class="hero is-fullheight-with-navbar main_homepage">
      <div class="hero-body">
        <div class="container has-text-centered ml-4 pl-4">
          <h1 class="title">Welcome!</h1>
          <p class="text">Book your perfect holiday now.</p>
        </div>
      </div>
    </section>

    <div id="info" class="container block ml-6 mt-1 align-items-center align-self-center">
      <div class="columns align-self-center is-flex justify-content-center pl-2 ml-6">
        <a href="#cottages"> <img  class="image rounded" src="../assets/images/cabin.png" alt="pic1"/></a>
        <a href="#adventures"><img class="image rounded" src="../assets/images/fishing.png" alt="pic1"/></a>
        <a href="#boats"><img class="image rounded" src="../assets/images/ship.png" alt="pic1"/></a>
      </div>
      <div class="content ml-5 has-text-centered">
        <h2>Explore nature</h2>
        <p>
          Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla accumsan,
          metus ultrices eleifend gravida, nulla nunc varius lectus, nec rutrum
          justo nibh eu lectus. Ut vulputate semper dui. Fusce erat odio,
          sollicitudin vel erat vel, interdum mattis neque.
        </p>
      </div>
      <div class="columns is-8 is-variable mt-6 ml-4">
        <div class="column content has-text-centered">
          <h4>Lorem ipsum</h4>
          <p>
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla accumsan,
            metus ultrices eleifend gravida, nulla nunc varius lectus, nec rutrum
            justo nibh eu lectus. Ut vulputate semper dui. Fusce erat odio,
            sollicitudin vel erat vel, interdum mattis neque.
          </p>
        </div>
      </div>
    </div>

    <div id="cottages" class="block p-6 mt-6 mb-3" style="background: aliceblue">
      <div class="container ml-5 content align-items-center">
        <h2>Cottages</h2>
        <br />
        <div class="columns">
          <div class="column" v-for="c in cottages.slice(0,4)">
            <div class="card col-3" v-on:click="openCottage(c.id)">
              <div class="card-image">
                <carousel :per-page="1" :navigationEnabled="false" :mouse-drag="false" :autoplay="true" :paginationEnabled="false" v-bind:loop="true" v-bind:speed="3000" >
                  <slide v-for="url in c.images">
                    <img class="d-block w-100" :src="url" alt="First slide" style="height: 250px">
                  </slide>
                </carousel>
              </div>

              <div class="p-3">
                <h4>{{ c.name }}</h4>
                <p><font-awesome-icon icon="fa-map-marker"/> {{ c.address.street }}</p>
                <p>{{ c.address.city}}</p>
                <p><b>$<span>{{ c.price }}</span></b></p>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="has-text-centered mt-6 ml-0 content align-items-center">
        <a href="#/cottages" class="button view_button is-primary">View more</a>
      </div>
    </div>

    <div id="boats" class="block p-6 mt-1 mb-3" style="background: aliceblue">
      <div class="container ml-5 content align-items-center">
        <h2>Boats</h2>
        <br />
        <div class="columns">
          <div class="column" v-for="b in boats.slice(0,4)">
            <div class="card col-3" v-on:click="openBoat(b.id)">
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

    <div id="adventures" class="block p-6 mt-1 mb-6" style="background: aliceblue">
      <div class="container ml-5 content align-items-center">
        <h2>Fishing instructors</h2>
        <br />
        <div class="columns align-items-center is-flex justify-content-center">
          <div class="column" v-for="i in instructors.slice(0,4)">
            <div class="card col-3">
              <div class="card-image">
                <img v-on:click="openInstructor(i.id)" class="d-block w-100" :src="i.mainPhoto" alt="img" style="height: 250px">
              </div>
              <div class="p-3">
                <h4>{{ i.name }} {{i.surname}}</h4>
                <p><font-awesome-icon icon="fa-map-marker"/> {{ i.address.street }}</p>
                <p>{{ i.address.city}}</p>
                <p><b><span>{{ i.biography }}</span></b></p>
                <button class="button view_button is-primary" v-on:click="openAdventures(i.id)">Adventures</button>
              </div>
            </div>
          </div>
        </div>
      </div>


    </div>



  </div>
</template>


<script>
import "bulma/css/bulma.css";
import axios from "axios";
export default {
  name: "Homepage",
  data: function(){
    return{
      cottages: '',
      boats: '',
      adventures: '',
      instructors: '',
      imagesUrl:[]
    }
  },
  mounted: function (){
    axios
      .get(process.env.VUE_APP_SERVER_PORT+"/api/cottages/all")
      .then(response => {this.cottages = response.data
                        response.data.forEach(cottage => {
                          var images = []
                          cottage.images.forEach(image => {
                            axios.get(process.env.VUE_APP_SERVER_PORT+"/api/images/getImage/"+image.path,{responseType:"blob"})
                              .then(response => {
                                images.push(URL.createObjectURL(response.data));
                                  cottage.images = images;
                              })
                              .catch((error) =>{
                                console.log(error);
                              });
                          });
                        });
      });

    axios
      .get(process.env.VUE_APP_SERVER_PORT+"/api/boats/all")
      .then(response => {this.boats = response.data
                    response.data.forEach(boat => {
                    var images = []
                      boat.images.forEach(image => {
                      axios.get(process.env.VUE_APP_SERVER_PORT+"/api/images/getImage/"+image.path,{responseType:"blob"})
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
    axios
      .get(process.env.VUE_APP_SERVER_PORT+"/api/adventures/all")
      .then(response => {this.adventures = response.data
                        response.data.forEach(adventure => {
                          var images = []
                          adventure.images.forEach(image => {
                            axios.get(process.env.VUE_APP_SERVER_PORT+"/api/images/getImage/"+image.path,{responseType:"blob"})
                              .then(response => {
                                images.push(URL.createObjectURL(response.data));
                                adventure.images = images;
                              })
                              .catch((error) =>{
                                console.log(error);
                              });
                          });
                        });
      })

    axios
      .get(process.env.VUE_APP_SERVER_PORT+"/api/fishingInstructor/all")
      .then(response => {this.instructors = response.data
        response.data.forEach(i => {
          let image = i.mainPhoto;
          axios.get(process.env.VUE_APP_SERVER_PORT+"/api/images/getImage/"+image,{responseType:"blob"})
            .then(response => {
              i.mainPhoto = URL.createObjectURL(response.data);
            })
            .catch((error) =>{
              console.log(error);
            });
        });
      })
  },
  methods:{
    openCottage:function (id){
      this.$router.push({name:"CottageProfile",params:{id:id}});
    },
    openBoat:function (id){
      this.$router.push({name:"BoatProfile",params:{id:id}});
    },
    openAdventures:function (id){
      this.$router.push({name:"BrowseAdventures",params:{id:id}});
    },

    loadOnlyOneImage:function (name) {
      axios.get(process.env.VUE_APP_SERVER_PORT+"/api/images/getImage/"+name,{responseType:"blob"})
        .then(response => {
          this.imagesUrl.push(URL.createObjectURL(response.data));
        })
        .catch((error) =>{
          console.log(error);
        });
    },
    openInstructor: function (id){
      this.$router.push({name:"AdvertiserProfile",params:{id:id}});
    }

  }
}
</script>

<style scoped>
.main_homepage {
  background-attachment: fixed;
  background-image: url('../assets/images/back2.jpg');
  background-position-y: 0;
  background-repeat: no-repeat;
  background-size: cover;
  height: 50%;
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

.image{
  height: 170px;
  width: 170px;
  padding: 50px;
  transition: transform .3s;

}

.image:hover{
  transform: scale(1.4);
}

</style>
