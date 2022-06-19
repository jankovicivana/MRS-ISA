<template>
  <div class="my-4 browse-card">
    <card class="media shadow p-4" style="background-color: white">
      <figure class="media-left">
        <p class="image">
          <a href="/#" >
            <img class="image" :src="photo" alt="Image"/>
          </a>
        </p>
      </figure>
      <div class="media-content">
        <div class="content">
          <div>
            <p>
              <span class="columns mt-0 mb-1">
                <span class="col-10 ml-3">
                  <strong><router-link :to="{ name:'CottageProfile',params:{id:cottage.id} }"  style="color: #2e6b6b; font-size: 20px">{{cottage.name}}</router-link> </strong>
                </span>
                <span class="col-2">
                  <star-rating :rating="cottage.averageGrade" :read-only="true" :increment="0.1" :star-size="20" :size="200"></star-rating>
                </span>
              </span>

              <b><font-awesome-icon icon="fa-map-marker"/> {{cottage.address.street}}, {{cottage.address.city}}, {{cottage.address.country}}</b>
              <br /> <br />
              {{cottage.description}}
            </p>
            <div>
              <h4>$ <span>{{cottage.price}}</span></h4>
            </div>
          </div>
          <div class="columns">
            <div class="column">
              <span class="tag m-lg-1" style="background-color: #2e6b6b; color: white" v-for="as in cottage.additionalServices.slice(0, 5)"><font-awesome-icon class="small-icon m-1" icon="fa-solid fa-check-circle" /> {{as.name}}</span>
            </div>
            <div
              class="column is-flex is-justify-content-flex-end is-align-items-right">
              <div class="buttons ml-5">
                <button v-if="role ==='ROLE_client'" class="button is-success" style="background-color: #2e6b6b" v-on:click="$emit('subscribe', cottage.id)">Subscribe</button>

                <router-link :to="{ name:'CottageProfile',params:{id:cottage.id} }" class="button view_button is-success" style="background-color: #2e6b6b">
                  View
                </router-link>

                <button class="button" v-if="canReserve" v-on:click="$emit('reserve', cottage)">Reserve</button>
                <button class="button" v-if="role === 'ROLE_admin'" v-on:click="$emit('deleteCottage',cottage.id)">Delete</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </card>
  </div>
</template>

<script>

import axios from "axios";

export default {
  name: "CottageBrowseCard",
  data: function(){
    return{
      role:'',
      photo:''
    }
  },  props: ['cottage', 'canReserve'],
  mounted() {
    this.role = sessionStorage.getItem("role");

    axios.get(process.env.VUE_APP_SERVER_PORT+"/api/images/getImage/"+this.cottage.images[0].path,{responseType:"blob"})
      .then(response => {
        this.photo = URL.createObjectURL(response.data);
      })
      .catch((error) =>{
        console.log(error);
      });
  },
  methods: {


  }
}
</script>

<style scoped>

.browse-card{
  transition: transform .2s;
  width: 100%;
}

.browse-card:hover{
  transform: scale(1.07);
}

.image {
  max-height: 200px;
  width: 250px;
}

.view_button{
  background-color: #2e6b6b;
  color: white;
}

button{
  background-color: #2e6b6b;
  color: white;
}
button:hover{
  background-color: #2e6b6b;
}


.view_button:hover{
  background-color: #4AAE9B;
}

a{
  text-decoration: none;
}

</style>
