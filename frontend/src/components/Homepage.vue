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

  </div>
</template>

<script>
export default {
  name: "Homepage"
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

</style>
