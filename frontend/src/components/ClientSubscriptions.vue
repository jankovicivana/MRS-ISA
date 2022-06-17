<template>
  <div class="subscriptions_main">
    <ClientNavbar></ClientNavbar>
    <div class="mt-5 container">
      <h1>My subscriptions</h1>

      <div v-if="subscriptions.length === 0">
        <p style="color: white"> No subscriptions for now. Subscribe to entities to receive info about special offers.</p>
      </div>
      <div v-for="s in subscriptions">
        <card class="media p-4 mb-4 browse-card" style="background-color: white">
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
                    <button class="button unsub_btn is-link" id="unsub" v-on:click="unsubscribe(s)">Unsubscribe</button>
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
      selected: '',
    }
  },
  mounted: function(){
    axios
      .get(process.env.VUE_APP_SERVER_PORT+"/api/sub/getSubscriptions", {headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
      .then(response => (this.subscriptions = response.data))
  },
  methods: {
    show: function(group, type=''){
      let title = `<p style="font-size: 25px">Unsubscribed!</p>`
      let text = `<p style="font-size: 15px">You won't recieve emails about special offers anymore.</p>`
      this.$notify({group, title, text, type})
    },

    unsubscribe: function (s) {
      let id = s.id;
      axios.delete(process.env.VUE_APP_SERVER_PORT+"/api/sub/delete/"+id, {headers: {Authorization:
        'Bearer ' + sessionStorage.getItem("accessToken")}})
        .then(response => {
          const index = this.subscriptions.indexOf(s);
          this.subscriptions.splice(index, 1);
          this.show('foo-css', 'success');
        }).catch(function error(error) {
        alert(error.response.data);
      });
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
  overflow: visible;
}

h1{
  color: white;
  padding-bottom: 30px;
}

#unsub{
  float: right;
  height: 30px;
  width: 170px;
  border: none;
  color: #fff;
  border-radius: 4px;
  background-color: cadetblue;
  cursor: pointer;
  text-transform: uppercase
}

#unsub:hover {
  background: darkcyan;
  color: white;
}

#unsub:focus {
  background: darkcyan;
  box-shadow: none
}

#unsub:active {
  background: darkcyan;
  box-shadow: none
}


.browse-card{
  transition: transform .2s;
}

.browse-card:hover{
  transform: scale(1.07);
}

a{
  text-decoration: none;
}

</style>
