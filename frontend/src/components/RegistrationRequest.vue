<template>
  <section class="basic_background vh-100" >
    <AdminNavbar :isAdmin="true"></AdminNavbar>
    <div class="content is-medium" style=" height:80%"  >
      <div class="mask d-flex align-items-center pt-5 h-100 gradient-custom-3"   >
        <div class="container h-100" >
          <div class="row d-flex justify-content-center  h-100"   >
            <div class="col-11 " >
              <div class="card " style="border-radius: 15px; background: #ecd9c6;  height: 100% ">

                <div class="card-body p-5">

                  <h3 class="title">Advertiser registration requests</h3>
                  <hr />
                  <div>
                    <table class="table">
                      <thead>
                      <tr >

                        <th colspan="2">Client</th>
                        <th colspan="2">Reason for registration</th>
                        <th colspan="2">Reason for rejecting registration</th>
                        <th></th>
                        <th></th>
                        <th></th>

                      </tr>
                      </thead>
                      <tbody>
                      <tr v-if="requests.length == 0">
                        <td colspan="9" class="p-3">There is no registration request.</td>
                      </tr>
                      <tr v-for="r in requests">
                        <td colspan="2"><router-link style="text-decoration: none;color: #2e6b6b" :to="{ name: 'AdvertiserProfile',params:{id:r.id} }" >{{r.surname + " "+ r.name}}</router-link></td>
                        <td colspan="2">{{r.registrationReason}}</td>
                        <td colspan="2"><textarea v-model="r.rejecting" style="width: 350px" ></textarea></td>
                        <td></td>

                        <td><button v-on:click="acceptRegistration(r)" style="background: #2e6b6b; border-radius: 8px;color: #FFFFFF; border-color: #FFFFFF">Accept</button></td>
                        <td><button v-on:click="rejectRegistration(r)" style="background: darkred; border-radius: 8px;color: #FFFFFF; border-color: #FFFFFF">Reject</button></td>
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
  name: "RegistrationRequest",
  components: {AdminNavbar},
  data(){
    return{
      requests:''
    }
  },
  mounted:function (){
    axios
      .get(process.env.VUE_APP_SERVER_PORT+"/api/user/getUsersOnHold", {headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
      .then(response => (
        this.requests = response.data,
        console.log('-------->'+this.requests[0].registration_status)

  ));

  },
  methods: {

    show: function(group, type='',title,text){
      this.$notify({group, title, text, type})
    },

    acceptRegistration:function (request){
      let id = request.id
      axios.get(process.env.VUE_APP_SERVER_PORT + "/api/user/acceptRegistration/"+id,{headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
        .then(response => {
          this.show('foo-css', 'success',`<p style="font-size: 25px">Successfull!</p>`,`<p style="font-size: 20px">Successfully accepted registration!</p>`)
          setTimeout(() => {}, 3000)
          const index = this.requests.indexOf(request);
          this.requests.splice(index, 1);
        }).catch(function error(error) {
        alert(error.response);
      });
    },
    rejectRegistration:function (request){
      let id = request.id
      let area = request.rejecting
      if (area==='' || area===null){
        this.show('foo-css', 'error',`<p style="font-size: 25px">Warning!</p>`,`<p style="font-size: 20px">Must enter rejecting reason!</p>`)
        return;
      }
      this.info = {
        id:request.id,
        name: request.name,
        surname: request.surname,
        email: request.email,
        reason: area
      };

      axios.post(process.env.VUE_APP_SERVER_PORT + "/api/user/rejectRegistration",this.info,{headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
        .then(response => {
          this.show('foo-css', 'success',`<p style="font-size: 25px">Successfull!</p>`,`<p style="font-size: 20px">Successfully rejected registration!</p>`)
          setTimeout(() => {}, 3000)
          const index = this.requests.indexOf(request);
          this.requests.splice(index, 1);
        }).catch(function error(error) {
        alert(error.response);
      });
    }

  }
}
</script>

<style scoped>
  textarea:focus{
    border: 2px solid dodgerblue;
  }
</style>
