<template>
  <div>
    <section class="hero is-info is-large hero-main">
      <div class="hero-body">
        <div class="container has-text-centered">
          <h1 class="title">Welcome!</h1>
          <p class="subtitle">Book your perfect holiday now.</p>
        </div>
      </div>

      <div class="hero-foot">
        <nav class="tabs is-boxed is-fullwidth justify-content-center">
          <div class="container">
            <ul>
              <li class="is-active">
                <a href="#">Overview</a>
              </li>
              <li>
                <a href="#/boats">Boats</a>
              </li>
              <li>
                <a href="#/cottages">Cottages</a>
              </li>
              <li>
                <a href="#/adventures">Fishing Adventures</a>
              </li>
            </ul>
          </div>
        </nav>
      </div>
    </section>

    <div id="overview" class="container block mt-6 ml-6 align-items-center align-self-center">
      <div class="content is-medium ml-6 has-text-centered " >
        <h1>Explore nature</h1>
        <p>
          Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla accumsan,
          metus ultrices eleifend gravida, nulla nunc varius lectus, nec rutrum
          justo nibh eu lectus. Ut vulputate semper dui. Fusce erat odio,
          sollicitudin vel erat vel, interdum mattis neque.
        </p>
      </div>
      <div class="columns is-8 is-variable mt-6 ml-4">
        <div class="column block content">
          <h4>Lorem ipsum</h4>
          <p>
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla accumsan,
            metus ultrices eleifend gravida, nulla nunc varius lectus, nec rutrum
            justo nibh eu lectus. Ut vulputate semper dui. Fusce erat odio,
            sollicitudin vel erat vel, interdum mattis neque.
          </p>
        </div>
        <div class="column block content">
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
      <div class="container content is-medium">
        <h1>Cottages</h1>
        <hr />
        <br />
        <div class="columns">
          <div class="column" v-for="c in cottages">
            <div class="card zoom col-3">
              <div class="card-image">
                <img class="cottage-image" :src="require('../assets/images/cottage'+c.id+'.jpg')" />
              </div>

              <div class="content p-3">
                <h4>{{ c.name }}</h4>
                <p>{{ c.address.street }}</p>
                <p>{{ c.address.city}}</p>
                <p>$<span>{{ c.price }}</span></p>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="has-text-centered mt-6">
        <a href="#/cottages" class="button is-primary">View all</a>
      </div>
    </div>


    <div id="boats" class="block p-6 mt-1 mb-3" style="background: aliceblue">
      <div class="container content is-medium">
        <h1>Boats</h1>
        <hr />
        <br />
        <div class="columns">
          <div class="column" v-for="b in boats">
            <div class="card zoom col-3">
              <div class="card-image">
                <img class="cottage-image" :src="require('../assets/images/boat'+ (parseInt(b.id)-2).toString() +'.jpg')" />
              </div>

              <div class="content p-3">
                <h4>{{ b.name }}</h4>
                <p>{{ b.address.street }}</p>
                <p>{{ b.address.city}}</p>
                <p>$<span>{{ b.price }}</span></p>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="has-text-centered mt-6">
        <a href="#/boats" class="button is-primary">View all</a>
      </div>
    </div>

    <div id="adventures" class="block p-6 mt-1 mb-6" style="background: aliceblue">
      <div class="container content is-medium">
        <h1>Adventures</h1>
        <hr />
        <br />
        <div class="columns">
          <div class="column" v-for="a in adventures">
            <div class="card zoom col-3">
              <div class="card-image">
                <img class="cottage-image" :src="require('../assets/images/pic1.jpg')" />
              </div>

              <div class="content p-3">
                <h4>{{ a.name }}</h4>
                <p>{{ a.address.street }}</p>
                <p>{{ a.address.city}}</p>
                <p>$<span>{{ a.price }}</span></p>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="has-text-centered mt-6">
        <a href="#/adventures" class="button is-primary">View all</a>
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
      adventures: ''
    }
  },
  mounted: function (){
    axios
      .get(process.env.VUE_APP_SERVER_PORT+"/api/cottages/all")
      .then(response => (this.cottages = response.data))
    axios
      .get(process.env.VUE_APP_SERVER_PORT+"/api/boats/all")
      .then(response => (this.boats = response.data))
    axios
      .get(process.env.VUE_APP_SERVER_PORT+"/api/adventures/all")
      .then(response => (this.adventures = response.data))
  },
}
</script>

<style scoped>
.hero-main {
  background:
    url("../assets/images/back2.jpg");
  background-size: cover;
}

.title {
  font-size: 3rem;
}

.subtitle {
  font-size: 2rem;
}

* {
  margin: 0;

  box-sizing: border-box;
}

body {
  font-family: Century Gothic, sans-serif;
}

html {
  scroll-behavior: smooth;
}

.cottage-image {
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
}

</style>
