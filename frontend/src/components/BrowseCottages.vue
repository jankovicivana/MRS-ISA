<template>
  <div class="browse_main">
    <main_navbar v-if="role === ''"></main_navbar>
    <cottage-owner-navbar v-if="role === 'ROLE_cottageOwner'"></cottage-owner-navbar>
    <client-navbar v-if="role === 'ROLE_client'"></client-navbar>
    <AdminNavbar v-if="role === 'ROLE_admin'" :isAdmin="true"></AdminNavbar>
    <div class="mt-5 container">
      <h1>Cottages</h1>
      <hr style="color: #2e6b6b"/>
      <div>
        <div>
          <div class="columns mb-6">
            <div class="column col-2">
              <input class="input is-link" type="text" placeholder="Browse cottages" v-model="searchText"/>
            </div>

            <div class="column col-3">
              <div class="select">
                <select title="Sorting" v-model="searchSort">
                  <option selected="selected" value="NO_SORT" >No sorting</option>
                  <option value="PRICE_ASC">Price: Ascending</option>
                  <option value="PRICE_DESC">Price: Descending</option>
                  <option value="RATING_ASC">Rating: Ascending</option>
                  <option value="RATING_DESC">Rating: Descending</option>
                  <option value="NAME_ASC">Name: Ascending</option>
                  <option value="NAME_DESC">Name: Descending</option>
                  <option value="ADDRESS_ASC">Address: Ascending</option>
                  <option value="ADDRESS_DESC">Address: Descending</option>
                </select>
              </div>
            </div>

            <div class="column is-flex is-justify-content-flex-end is-1">
              <button class="button search_button is-link"  v-on:click="search()">Search</button>
            </div>
          </div>
        </div>
        <div v-if="cottages.length === 0">
          <p style="color: white"> No cottages for now.</p>
        </div>
        <div v-for="c in search_cottages">
          <browse_card :cottage="c" v-on:deleteCottage="deleteCottage($event)" v-on:subscribe="subscribe($event)"></browse_card>
        </div>
      </div>
    </div>
    <div style="height: 150px"></div>
  </div>
</template>

<script>
import axios from "axios";
import MainNavbar from "./header/MainNavbar";
import CottageBrowseCard from "./CottageBrowseCard";
import CottageOwnerNavbar from "./header/CottageOwnerNavbar";
import ClientNavbar from "./header/ClientNavbar";
import AdminNavbar from "./header/AdminNavbar";

export default {
  name: "Cottages",
  components: {AdminNavbar, ClientNavbar, 'browse_card': CottageBrowseCard, 'main_navbar': MainNavbar,CottageOwnerNavbar},
  data: function(){
    return{
      cottages: '',
      searchText: '',
      searchSort: '',
      search_cottages: '',
      role:'',
      can: ''
    }
  },
  mounted: function (){
    this.role = sessionStorage.getItem("role");
    if (this.role === "ROLE_cottageOwner") {
      axios
        .get(process.env.VUE_APP_SERVER_PORT+"/api/cottages/allByOwner", {headers: {Authorization:
              'Bearer ' + sessionStorage.getItem("accessToken")}})
        .then(response => (this.cottages = this.search_cottages = response.data))
    }else{
      axios
        .get(process.env.VUE_APP_SERVER_PORT+"/api/cottages/all", {headers: {Authorization:
              'Bearer ' + sessionStorage.getItem("accessToken")}})
        .then(response => (this.cottages = this.search_cottages = response.data))
    }

  },
  methods: {
    search: function (){
      if(this.searchText){
        this.search_cottages = this.cottages.filter(item => {
          return item.name.toLowerCase().includes(this.searchText.toLowerCase());
        })
      }
      else{
        this.search_cottages = this.cottages;
      }
      if(this.searchSort !== "NO_SORT"){
        if (this.searchSort === "NAME_ASC"){
          this.search_cottages = this.search_cottages.sort((a, b) => {
            let aName = a.name.toLowerCase();
            let bName = b.name.toLowerCase();
            if(aName < bName){
              return -1;
            }
            if(aName > bName){
              return 1;
            }
            return 0;
          })
        }
        else if(this.searchSort === "NAME_DESC"){
          this.search_cottages = this.search_cottages.sort((a, b) => {
            let aName = a.name.toLowerCase();
            let bName = b.name.toLowerCase();
            if(aName < bName){
              return 1;
            }
            if(aName > bName){
              return -1;
            }
            return 0;
          })
        }

        else if(this.searchSort === "PRICE_ASC"){
          this.search_cottages = this.search_cottages.sort((a, b) => {

            if(a.price < b.price){
              return -1;
            }
            if(a.price > b.price){
              return 1;
            }
            return 0;
          })
        }

        else if(this.searchSort === "PRICE_DESC"){
          this.search_cottages = this.search_cottages.sort((a, b) => {

            if(a.price < b.price){
              return 1;
            }
            if(a.price > b.price){
              return -1;
            }
            return 0;
          })
        }

        else if(this.searchSort === "RATING_ASC"){
          this.search_cottages = this.search_cottages.sort((a, b) => {

            if(a.rating < b.rating){
              return -1;
            }
            if(a.rating > b.rating){
              return 1;
            }
            return 0;
          })
        }

        else if(this.searchSort === "RATING_DESC"){
          this.search_cottages = this.search_cottages.sort((a, b) => {

            if(a.rating < b.rating){
              return 1;
            }
            if(a.rating > b.rating){
              return -1;
            }
            return 0;
          })
        }

        else if (this.searchSort === "ADDRESS_ASC"){
          this.search_cottages = this.search_cottages.sort((a, b) => {
            let aAddress = a.address.street.toLowerCase();
            let bAddress = b.address.street.toLowerCase();
            if(aAddress < bAddress){
              return -1;
            }
            if(aAddress > bAddress){
              return 1;
            }
            return 0;
          })
        }
        else if(this.searchSort === "ADDRESS_DESC"){
          this.search_cottages = this.search_cottages.sort((a, b) => {
            let aAddress = a.address.street.toLowerCase();
            let bAddress = b.address.street.toLowerCase();
            if(aAddress < bAddress){
              return 1;
            }
            if(aAddress > bAddress){
              return -1;
            }
            return 0;
          })
        }

      }
    },
    show: function (group, type = '',title, text) {
      this.$notify({group, title, text, type})
    },

    deleteCottage:function (id){
      axios.delete(process.env.VUE_APP_SERVER_PORT+"/api/cottages/deleteCottage/"+id, {headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
        .then(response => {
          this.show('foo-css', 'success',`<p style="font-size: 25px">Successfully deleted!</p>`,`<p style="font-size: 20px">Successfully deleted cottage!</p>`)
          setTimeout(() => {location.reload(); }, 3000)
        }).catch((error) => {
        this.show('foo-css', 'error',`<p style="font-size: 25px">Deletion is not possible!</p>`,`<p style="font-size: 20px">Cottage has reservations.</p>`)
      });

    },

    subscribe: function (id){
      axios
        .get(process.env.VUE_APP_SERVER_PORT+"/api/sub/subscribe/"+id, {headers: {Authorization:
              'Bearer ' + sessionStorage.getItem("accessToken")}})
        .then(response => {
          if (response.data === "Already subscribed"){
            this.show('foo-css', 'warning',`<p style="font-size: 25px">Subscribed</p>`,`<p style="font-size: 20px">You are already subscribed!</p>`)
          } else{
            this.show('foo-css', 'success',`<p style="font-size: 25px">Successfully subscribed!</p>`,`<p style="font-size: 20px">You will now receive email notifications about special offers.</p>`)

          }
    });
    },

  /*  canSubscribe: function (c){
      const res = this.checkSubscribe(c).then(value => {return value})
      return res
    },

    checkSubscribe: function (c){
      if(this.role !== "ROLE_client") return false;
      const res =  axios
        .get(process.env.VUE_APP_SERVER_PORT + "/api/sub/canSubscribe/" + c.id, {
          headers: {
            Authorization:
              'Bearer ' + sessionStorage.getItem("accessToken")
          }
        }).then(response => {return response.data});
      return res
    }*/

    }
}
</script>

<style scoped>

.browse_main {
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

.search_button{
  background-color: #2e6b6b;
  color: white;
}

.search_button:hover{
  background-color: #4AAE9B;
}


</style>
