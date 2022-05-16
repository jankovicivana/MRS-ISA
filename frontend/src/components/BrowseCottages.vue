<template>
  <div class="browse_main">
    <main_navbar></main_navbar>
    <div class="mt-5 container">
      <h1>Cottages</h1>
      <hr style="color: #2e6b6b"/>
          <div class="columns is-multiline mb-5 is-vcentered">
            <div class="column is-2">
              <input
                class="input is-link"
                type="text"
                placeholder="Search anything!"
                v-model="searchText"
              />
            </div>

            <div class="column is-3">
              <div class="select">
                <select title="Sorting" v-model="searchSort">
                  <option selected="selected" value="NO_SORT" >No sorting</option>
                  <option value="PRICE_ASC">Price: Ascending</option>
                  <option value="PRICE_DES">Price: Descending</option>
                  <option value="RATING_ASC">Rating: Ascending</option>
                  <option value="RATING_DES">Rating: Descending</option>
                  <option value="NAME_ASC">Name: Ascending</option>
                  <option value="NAME_DES">Name: Descending</option>
                  <option value="ADDR_ASC">Address: Ascending</option>
                  <option value="ADDR_DES">Address: Descending</option>
                </select>
              </div>
            </div>



            <div class="column is-flex is-justify-content-flex-end is-1">
              <button class="button search_button is-link"  v-on:click="search()">Search</button>
            </div>
          </div>
        <div v-if="cottages.length === 0">
          <p style="color: white"> No cottages for now.</p>
        </div>
        <div v-for="c in search_cottages">
          <browse_card :cottage="c"></browse_card>
        </div>
      </div>
    <div style="height: 150px"></div>
  </div>
</template>

<script>
import axios from "axios";
import MainNavbar from "./header/MainNavbar";
import CottageBrowseCard from "./CottageBrowseCard";
export default {
  name: "Cottages",
  components: {'browse_card': CottageBrowseCard, 'main_navbar': MainNavbar},
  data: function(){
    return{
      cottages: '',
      searchText: '',
      searchSort: '',
      search_cottages: ''
    }
  },
  mounted: function (){
    axios
      .get(process.env.VUE_APP_SERVER_PORT+"/api/cottages/all")
      .then(response => (this.cottages = this.search_cottages = response.data))
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
        else if(this.searchSort === "NAME_DES"){
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

        else if(this.searchSort === "PRICE_DES"){
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

        else if(this.searchSort === "RATING_DES"){
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

        else if (this.searchSort === "ADDR_ASC"){
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
        else if(this.searchSort === "ADDR_DES"){
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
    }
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
