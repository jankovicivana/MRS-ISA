<template>
  <section class="basic_background vh-100" >
    <admin-navbar :isAdmin="true"></admin-navbar>
    <div class="content is-medium" style=" height:80%"  >
      <div class="mask d-flex align-items-center pt-5 h-100 gradient-custom-3"   >
        <div class="container h-100" >
          <div class="row d-flex justify-content-center  h-100"   >
            <div class="col-11 " >
              <div class="card " style="border-radius: 15px; background: #ecd9c6;  height: 100% ">
                <div class="card-body p-5">

                  <h3 class="title">Advertiser complaints</h3>
                  <hr />
                  <div>
                    <table class="table">
                      <thead>
                      <tr >

                        <th>Client</th>
                        <th>Advertiser</th>
                        <th>Complaint</th>
                        <th>Response</th>
                        <th></th>
                        <th></th>
                        <th></th>

                      </tr>
                      </thead>
                      <tbody>
                      <tr v-if="advertiserComplaints.length == 0">
                        <td colspan="7" class="p-3">There is no advertiser complaints.</td>
                      </tr>
                      <tr  v-for="c in advertiserComplaints">
                        <td>{{c.client.surname + " "+ c.client.name}}</td>
                        <td>{{c.advertiser.surname + " "+ c.advertiser.name}}</td>
                        <td>{{c.complaint}}</td>
                        <td colspan="2"><textarea v-model="c.answer" style="width: 350px" ></textarea></td>
                        <td><button v-on:click="acceptAdvertiserComplaint(c)" style="background: #2e6b6b; border-radius: 8px;color: #FFFFFF; border-color: #FFFFFF">Accept</button></td>
                        <td><button v-on:click="rejectAdvertiserComplaint(c)" style="background: darkred; border-radius: 8px;color: #FFFFFF; border-color: #FFFFFF">Reject</button></td>
                      </tr>
                      </tbody>

                    </table>
                  </div>

                </div>
                <div class="card-body p-5">

                  <h3 class="title">Entity complaints</h3>
                  <hr />
                  <div>
                    <table class="table">
                      <thead>
                      <tr >

                        <th>Client</th>
                        <th>Entity</th>
                        <th>Complaint</th>
                        <th>Response</th>
                        <th></th>
                        <th></th>
                        <th></th>

                      </tr>
                      </thead>
                      <tbody>
                      <tr  v-if=" entityComplaints.length == 0">
                        <td colspan="7" class="p-3">There is no entity complaints.</td>
                      </tr>
                      <tr v-for="c in entityComplaints">
                        <td>{{c.client.surname + " "+ c.client.name}}</td>
                        <td>{{c.entity.name}}</td>
                        <td>{{c.complaint}}</td>
                        <td colspan="2"><textarea v-model="c.answer" style="width: 350px" ></textarea></td>
                        <td><button v-on:click="acceptEntityComplaint(c)" style="background: #2e6b6b; border-radius: 8px;color: #FFFFFF; border-color: #FFFFFF">Accept</button></td>
                        <td><button v-on:click="rejectEntityComplaint(c)" style="background: darkred; border-radius: 8px;color: #FFFFFF; border-color: #FFFFFF">Reject</button></td>
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
import AdminNavbar from "./header/AdminNavbar";
import axios from "axios";
export default {
  name: "AdminReportResponse",
  components: {AdminNavbar},
  data(){

    return{
      advertiserComplaints: '',
      entityComplaints:''
    }
  }
  ,mounted:function (){
    axios
      .get(process.env.VUE_APP_SERVER_PORT+"/api/complaint/getOnHoldAdvertiserComplaint", {headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
      .then(response => (
        this.advertiserComplaints = response.data
      ));

    axios
      .get(process.env.VUE_APP_SERVER_PORT+"/api/complaint/getOnHoldEntityComplaint", {headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
      .then(response => (
        this.entityComplaints = response.data
      ))


  },
  methods:{
    show: function(group, type='',title,text){
      this.$notify({group, title, text, type})
    },
    acceptAdvertiserComplaint:function (review) {
      let id = review.id
      let area = review.answer
      if (area==='' || area===null){
        this.show('foo-css', 'error',`<p style="font-size: 25px">Warning!</p>`,`<p style="font-size: 20px">Must enter response for this request!</p>`)
        return;
      }
      axios.post(process.env.VUE_APP_SERVER_PORT + "/api/complaint/acceptAdvertiserComplaint",review,{headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
        .then(response => {
          this.show('foo-css', 'success',`<p style="font-size: 25px">Successfull!</p>`,`<p style="font-size: 20px">Successfully accepted advertiser complaint!</p>`)
          setTimeout(() => {
            location.reload();
          }, 3000)
          const index = this.advertiserComplaints.indexOf(review);
          this.advertiserComplaints.splice(index, 1);
        }).catch(function error(error) {
        alert(error.response);
      });
    },
    acceptEntityComplaint:function (review) {
      let id = review.id
      let area = review.answer
      if (area==='' || area===null){
        this.show('foo-css', 'error',`<p style="font-size: 25px">Warning!</p>`,`<p style="font-size: 20px">Must enter response for this request!</p>`)
        return;
      }
      axios.post(process.env.VUE_APP_SERVER_PORT + "/api/complaint/acceptEntityComplaint" , review, {
        headers: {
          Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")
        }
      })
        .then(response => {
          this.show('foo-css', 'success',`<p style="font-size: 25px">Successfull!</p>`,`<p style="font-size: 20px">Successfully accepted entity complaint!</p>`)
          setTimeout(() => {
            //location.reload();
          }, 3000)
          const index = this.entityComplaints.indexOf(review);
          this.entityComplaints.splice(index, 1);
        }).catch(function error(error) {
        alert(error.response);
      });
    },
    rejectEntityComplaint:function (review) {
      let id = review.id
      let area = review.answer
      if (area==='' || area===null){
        this.show('foo-css', 'error',`<p style="font-size: 25px">Warning!</p>`,`<p style="font-size: 20px">Must enter response for this request!</p>`)
        return;
      }
      axios.post(process.env.VUE_APP_SERVER_PORT + "/api/complaint/rejectEntityComplaint" , review, {
        headers: {
          Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")
        }
      })
        .then(response => {
          this.show('foo-css', 'success',`<p style="font-size: 25px">Successfull!</p>`,`<p style="font-size: 20px">Successfully rejected entity complaint!</p>`)
          setTimeout(() => {
            //location.reload();
          }, 3000)
          const index = this.entityComplaints.indexOf(review);
          this.entityComplaints.splice(index, 1);
        }).catch(function error(error) {
        alert(error.response);
      });
    },
    rejectAdvertiserComplaint:function (review) {
      let id = review.id
      let area = review.answer
      if (area==='' || area===null){
        this.show('foo-css', 'error',`<p style="font-size: 25px">Warning!</p>`,`<p style="font-size: 20px">Must enter response for this request!</p>`)
        return;
      }
      axios.post(process.env.VUE_APP_SERVER_PORT + "/api/complaint/rejectAdvertiserComplaint",review,{headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
        .then(response => {
          this.show('foo-css', 'success',`<p style="font-size: 25px">Successfull!</p>`,`<p style="font-size: 20px">Successfully reject advertiser complaint!</p>`)
          setTimeout(() => {
            //location.reload();
          }, 3000)
          const index = this.advertiserComplaints.indexOf(review);
          this.advertiserComplaints.splice(index, 1);
        }).catch(function error(error) {
        alert(error.response);
      });
    }

  }
}
</script>

<style scoped>

</style>
