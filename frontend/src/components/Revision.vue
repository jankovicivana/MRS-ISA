<template>
  <section class="basic_background vh-100" >
    <admin-navbar></admin-navbar>
    <div class="content is-medium" style=" height:80%"  >
      <div class="mask d-flex align-items-center pt-5 h-100 gradient-custom-3"   >
        <div class="container h-100" >
          <div class="row d-flex justify-content-center  h-100"   >
            <div class="col-11 " >
              <div class="card " style="border-radius: 15px; background: #ecd9c6;  height: 100% ">
                <div class="card-body p-5">

                  <h3 class="title">Advertiser review</h3>
                  <hr />
                  <div>
                    <table class="table">
                      <thead>
                      <tr style="background: #ecd9c6">

                        <th>Client</th>
                        <th>Advertiser</th>
                        <th>Grade</th>
                        <th>Review</th>
                        <th></th>
                        <th></th>

                      </tr>
                      </thead>
                      <tbody>
                      <tr style="background: #ecd9c6"  v-if="advertiserReviews.length == 0">
                        <td colspan="6" class="p-3">There is no advertiser reviews.</td>
                      </tr>
                      <tr style="background: #ecd9c6;" v-for="review in advertiserReviews">
                        <td>{{review.client.surname + " "+ review.client.name}}</td>
                        <td>{{review.advertiser.surname + " "+ review.advertiser.name}}</td>
                        <td>{{review.grade}}</td>
                        <td>{{review.review}}</td>

                        <td><button v-on:click="acceptAdvertiserReview(review)" style="background: #2e6b6b; border-radius: 8px;color: #FFFFFF; border-color: #FFFFFF">Accept</button></td>
                        <td><button v-on:click="rejectAdvertiserReview(review)" style="background: darkred; border-radius: 8px;color: #FFFFFF; border-color: #FFFFFF">Reject</button></td>
                      </tr>
                      </tbody>

                    </table>
                  </div>

                </div>
                <div class="card-body p-5">

                  <h3 class="title">Entity review</h3>
                  <hr />
                  <div>
                    <table class="table">
                      <thead>
                      <tr style="background: #ecd9c6">

                        <th>Client</th>
                        <th>Entity</th>
                        <th>Grade</th>
                        <th>Review</th>
                        <th></th>
                        <th></th>

                      </tr>
                      </thead>
                      <tbody>
                      <tr style="background: #ecd9c6"  v-if="entityReviews.length == 0">
                        <td colspan="6" class="p-3">There is no entity reviews.</td>
                      </tr>
                      <tr style="background: #ecd9c6;" v-for="review in entityReviews">
                        <td>{{review.client.surname + " "+ review.client.name}}</td>
                        <td>{{review.entity.name}}</td>
                        <td>{{review.grade}}</td>
                        <td>{{review.review}}</td>

                        <td><button v-on:click="acceptEntityReview(review)" style="background: #2e6b6b; border-radius: 8px;color: #FFFFFF; border-color: #FFFFFF">Accept</button></td>
                        <td><button v-on:click="rejectEntityReview(review)" style="background: darkred; border-radius: 8px;color: #FFFFFF; border-color: #FFFFFF">Reject</button></td>
                      </tr>
                      </tbody>

                    </table>
                  </div>

                </div>
              </div>

            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
import axios from "axios";
import AdminNavbar from "./header/AdminNavbar";

export default {
  name: "Revision",
  components: {AdminNavbar},
  data(){

    return{
      advertiserReviews: '',
      entityReviews:''
    }
  }
  ,mounted:function (){
    axios
      .get(process.env.VUE_APP_SERVER_PORT+"/api/review/getOnHoldAdvertiserReview", {headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
      .then(response => (
        this.advertiserReviews = response.data
      ));

    axios
      .get(process.env.VUE_APP_SERVER_PORT+"/api/review/getOnHoldEntityReview", {headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
      .then(response => (
        this.entityReviews = response.data
      ))


  },
  methods:{
    show: function(group, type='',title,text){
      //let title = `<p style="font-size: 25px">Successfull!</p>`
      //let text = `<p style="font-size: 20px">Successfully deleted client!</p>`
      this.$notify({group, title, text, type})
    },
    acceptAdvertiserReview:function (review) {
      let id = review.id
      axios.get(process.env.VUE_APP_SERVER_PORT + "/api/review/acceptAdvertiserReview/"+id,{headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
        .then(response => {
          this.show('foo-css', 'success',`<p style="font-size: 25px">Successfull!</p>`,`<p style="font-size: 20px">Successfully accepted entity review!</p>`)
          setTimeout(() => {
            location.reload();
          }, 3000)
          const index = this.advertiserReviews.indexOf(review);
          this.advertiserReviews.splice(index, 1);
        }).catch(function error(error) {
        alert(error.response);
      });
    },
    acceptEntityReview:function (review) {
      let id = review.id
      axios.get(process.env.VUE_APP_SERVER_PORT + "/api/review/acceptEntityReview/" + id, {
        headers: {
          Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")
        }
      })
        .then(response => {
          this.show('foo-css', 'success',`<p style="font-size: 25px">Successfull!</p>`,`<p style="font-size: 20px">Successfully accepted entity review!</p>`)
          setTimeout(() => {
            //location.reload();
          }, 3000)
          const index = this.entityReviews.indexOf(review);
          this.entityReviews.splice(index, 1);
        }).catch(function error(error) {
        alert(error.response);
      });
    },
    rejectEntityReview:function (review) {
      let id = review.id
      axios.get(process.env.VUE_APP_SERVER_PORT + "/api/review/rejectEntityReview/" + id, {
        headers: {
          Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")
        }
      })
        .then(response => {
          this.show('foo-css', 'success',`<p style="font-size: 25px">Successfull!</p>`,`<p style="font-size: 20px">Successfully rejected entity review!</p>`)
          setTimeout(() => {
            //location.reload();
          }, 3000)
          const index = this.entityReviews.indexOf(review);
          this.entityReviews.splice(index, 1);
        }).catch(function error(error) {
        alert(error.response);
      });
    },
    rejectAdvertiserReview:function (review) {
      let id = review.id
      axios.get(process.env.VUE_APP_SERVER_PORT + "/api/review/rejectAdvertiserReview/"+id,{headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
        .then(response => {
          this.show('foo-css', 'success',`<p style="font-size: 25px">Successfull!</p>`,`<p style="font-size: 20px">Successfully reject entity review!</p>`)
          setTimeout(() => {
            //location.reload();
          }, 3000)
          const index = this.advertiserReviews.indexOf(review);
          this.advertiserReviews.splice(index, 1);
        }).catch(function error(error) {
        alert(error.response);
      });
    }

    }
}
</script>

<style scoped>

</style>
