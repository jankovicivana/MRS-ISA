<template>
  <div class="my-4 stretch-width browse-card">
    <article class="media shadow p-4" style="background-color: white">
      <figure class="media-left">
        <p class="image">
          <a href="/#" >
            <img class="image" :src="require('../assets/images/cottage'+entity.id+'.jpg')" alt="Image"/>
          </a>
        </p>
      </figure>
      <div class="media-content">
        <div class="content">
          <div>
            <p>
              <strong><a href="/#"  style="color: #2e6b6b">{{entity.name}}</a> </strong>
              <span class="mr-1 m-lg-4" style="font-size:20px; color:gold">&starf; &starf; &starf; &starf; &starf;</span>
              <br /><br />

              {{entity.description}}
            </p>

            <div>
              <strong><font-awesome-icon icon="fa-map-marker"/> {{entity.address.street}}, {{entity.address.city}}, {{entity.address.country}}</strong>
              <h4>$ <span>{{entity.price}}</span></h4>
            </div>
          </div>
          <div class="columns is-vcentered">
            <div class="column">
              <span class="tag m-lg-1" style="background-color: #2e6b6b; color: white" v-for="as in entity.additionalServices.slice(0, 5)"><font-awesome-icon class="small-icon m-1" icon="fa-solid fa-check-circle" /> {{as.name}}</span>
            </div>
            <div
              class="column is-flex is-justify-content-flex-end is-align-items-right">

              <div class="buttons ml-5">
                <a class="button is-success" href="/#" style="background-color: #2e6b6b">
                  View
                </a>
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
  name: "BrowseCard",
  props: ['entity']
}
</script>

<style scoped>

.browse-card{
  transition: transform .2s;
}

.browse-card:hover{
  transform: scale(1.07);
}

.image {
  max-height: 200px;
  width: 250px;
}

.stretch-width {
  width: 100% !important;
}

</style>
