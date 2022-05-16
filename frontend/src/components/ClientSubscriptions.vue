<template>
  <div class="subscriptions_main">
    <ClientNavbar></ClientNavbar>
    <div class="mt-5 container">
      <h1>My subscriptions</h1>
      <div class="columns">
        <div class="column col-3">
          <select v-model="selected">
            <option value="Cottages">Cottages</option>
            <option value="Boats">Boats</option>
            <option value="Adventures">Adventures</option>
          </select>
        </div>

      </div>
      <div v-if="subscriptions.length === 0">
        <p style="color: white"> No subscriptions for now. Subscribe to entities to receive info about special offers.</p>
      </div>
      <div v-for="s in subscriptions">
        <card class="media shadow p-4" style="background-color: white">
          <figure class="media-left">
            <p class="image">
              <a href="/#" >
                <img class="image" :src="require('../assets/images/cottage1.jpg')" alt="Image"/>
              </a>
            </p>
          </figure>
          <div class="media-content">
            <div class="content">
              <div>
                <p>
                  <strong><a href="/#"  style="color: #2e6b6b">{{s.entity.name}}</a> </strong>
                  <span class="mr-1 m-lg-4" style="font-size:20px; color:gold">&starf; &starf; &starf; &starf; &starf;</span>
                  <br /><br />
                  {{s.entity.description}}
                </p>
                <div>
                  <strong><font-awesome-icon icon="fa-map-marker"/> {{s.entity.address.street}}, {{s.entity.address.city}}, {{s.entity.address.country}}</strong>
                 <br />
                </div>
              </div>
              <div class="columns">
                <div class="column is-flex is-justify-content-flex-end is-align-items-right">
                  <div class="column is-justify-content-flex-end is-flex  col-2">
                    <button class="button search_button is-link"  v-on:click="unsubscribe()">Unsubscribe</button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </card>
      </div>
    </div>
  </div>
</template>

<script>
import ClientNavbar from "./header/ClientNavbar";
import axios from "axios";
export default {
  name: "ClientSubscriptions",
  components: {ClientNavbar},
  data: function(){
    return{
      subscriptions: '',
      selected: ''
    }
  },
  mounted: function(){
    axios
      .get(process.env.VUE_APP_SERVER_PORT+"/api/subscription/findByUser/2")
      .then(response => (this.subscriptions = response.data)).catch(function error(error) {
      alert(error.response.data);
    })
  },
  methods: {
    unsubscribe: function () {

    }
  }
}
</script>

<style scoped>

.subscriptions_main {
  background-attachment: fixed;
  background-image: url('../assets/images/back2.jpg');
  background-position-y: 0;
  background-repeat: no-repeat;
  background-size: cover;
  height: 100%;
  opacity: 1;
  position: absolute;
  top: 0;
  transition: opacity 0.3s linear 0s;
  width: 100%;
  overflow: scroll;
}

h1{
  color: white;
}

.unsub_button{
  background-color: #2e6b6b;
  color: white;
}

.unsub_button:hover{
  background-color: #4AAE9B;
}


</style>
