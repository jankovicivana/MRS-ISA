<template>
  <div class="my-4 browse-card">
    <article class="media shadow p-4" style="background-color: white">
      <figure class="media-left">
        <p class="image">
          <a href="/#" >
            <img v-if="adventure.id !== 2" class="image" :src="require('../assets/images/fish'+(parseInt(adventure.id)-7).toString()+'.jpg')" alt="Image"/>
            <img v-if="adventure.id === 2" class="image" :src="require('../assets/images/pic1.jpg')" alt="Image"/>
          </a>
        </p>
      </figure>
      <div class="media-content">
        <div class="content">
          <div>
            <p>
              <span class="columns mt-0 mb-1">
                <span class="col-10 ml-3">
                  <strong><a href="#/adventures/AdventureProfile"  style="color: #2e6b6b; font-size: 20px">{{adventure.name}}</a> </strong>
                </span>
                <span class="col-2">
                  <star-rating :rating="3.8" :read-only="true" :increment="0.01" :star-size="20" :size="200"></star-rating>
                </span>
              </span>

              <b><font-awesome-icon icon="fa-map-marker"/> {{adventure.address.street}}, {{adventure.address.city}}, {{adventure.address.country}}</b>
              <br /> <br />
              {{adventure.description}}
            </p>
            <div>
              <h4>$ <span>{{adventure.price}}</span></h4>
            </div>
          </div>
          <div class="columns">
            <div class="column">
              <span class="tag m-lg-1" style="background-color: #2e6b6b; color: white" v-for="as in adventure.additionalServices.slice(0, 5)"><font-awesome-icon class="small-icon m-1" icon="fa-solid fa-check-circle" /> {{as.name}}</span>
            </div>
            <div
              class="column is-flex is-justify-content-flex-end is-align-items-right">
              <div class="buttons ml-5">
                <button v-if="role === 'ROLE_client'" class="button is-success" style="background-color: #2e6b6b" v-on:click="$emit('subscribe', adventure.id)">Subscribe</button>
                <router-link :to="{ name:'AdventureProfile',params:{id:adventure.id} }" class="button view_button is-success" style="background-color: #2e6b6b">
                  View
                </router-link>
                <button class="button" v-if="canReserve" v-on:click="$emit('reserve', adventure)">Reserve</button>
                <button class="button" v-if="role === 'ROLE_admin'" v-on:click="$emit('deleteAdventure',adventure.id)">Delete</button>

              </div>
            </div>
          </div>
        </div>
      </div>
    </article>
  </div>
</template>

<script>
export default {
  name: "AdventureBrowseCard",
  data: function(){
    return{
      role:''
    }
  },
  props: ['adventure', 'canReserve'],
  mounted() {
    this.role = sessionStorage.getItem("role");
  }
}
</script>

<style scoped>

.browse-card{
  transition: transform .2s;
  width: 100%
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

.view_button:hover{
  background-color: #4AAE9B;
}


button{
  background-color: #2e6b6b;
  color: white;
}

a{
  text-decoration: none;
}

</style>
