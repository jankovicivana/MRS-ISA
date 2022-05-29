<template>
  <section class="basic_background vh-100" >
    <AdminNavbar></AdminNavbar>
    <div class="content is-medium" style=" height:80%"  >
      <div class="mask d-flex align-items-center pt-5 h-100 gradient-custom-3"   >
        <div class="container h-100" >
          <div class="row d-flex justify-content-center  h-100"   >
            <div class="col-11 " >
              <div class="card " style="border-radius: 15px; background: #ecd9c6;  height: 100% ">

                <div class="card-body p-5">

                  <h3 class="title">Delete profile requests</h3>
                  <hr />
                  <div>
                    <table class="table">
                      <thead>
                      <tr style="background: #ecd9c6">

                        <th colspan="2">Client</th>
                        <th colspan="2">Reason for deleting account</th>
                        <th colspan="2">Response</th>
                        <th></th>
                        <th></th>
                        <th></th>

                      </tr>
                      </thead>
                      <tbody>
                      <tr style="background: #ecd9c6"  v-if="requests.length == 0">
                        <td colspan="9" class="p-3">There is no registration request.</td>
                      </tr>
                      <tr style="background: #ecd9c6;" v-for="r in requests">
                        <td colspan="2">{{r.user.surname + " "+ r.user.name}}</td>
                        <td colspan="2">{{r.requestReason}}</td>
                        <td colspan="2"><textarea v-model="r.answer" style="width: 350px" ></textarea></td>
                        <td></td>

                        <td><button v-on:click="acceptDeletion(r)" style="background: #2e6b6b; border-radius: 8px;color: #FFFFFF; border-color: #FFFFFF">Accept</button></td>
                        <td><button v-on:click="rejectDeletion(r)" style="background: darkred; border-radius: 8px;color: #FFFFFF; border-color: #FFFFFF">Reject</button></td>
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
  name: "DeleteProfileRequests",
  components: {AdminNavbar},
  data(){
    return{
      requests:''
    }
  },
  mounted:function (){
    axios
      .get(process.env.VUE_APP_SERVER_PORT+"/api/user/getDeleteRequestsOnHold", {headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
      .then(response => (
        this.requests = response.data

      ));

  },
  methods: {
    show: function(group, type='',title,text){
      this.$notify({group, title, text, type})
    },

    acceptDeletion:function (request){
      let area = request.answer
      if (area==='' || area===null){
        this.show('foo-css', 'error',`<p style="font-size: 25px">Warning!</p>`,`<p style="font-size: 20px">Must enter response for this request!</p>`)
        return;
      }

      axios.post(process.env.VUE_APP_SERVER_PORT + "/api/user/acceptDeletion",request,{headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
        .then(response => {
          this.show('foo-css', 'success',`<p style="font-size: 25px">Successfull!</p>`,`<p style="font-size: 20px">Successfully accepted profile deletion!</p>`)
          setTimeout(() => {}, 3000)
          const index = this.requests.indexOf(request);
          this.requests.splice(index, 1);
        }).catch(function error(error) {
        alert(error.response);
      });


    },
    rejectDeletion:function (request){
      let area = request.answer
      if (area==='' || area===null){
        this.show('foo-css', 'error',`<p style="font-size: 25px">Warning!</p>`,`<p style="font-size: 20px">Must enter response for this request!</p>`)
        return;
      }

      axios.post(process.env.VUE_APP_SERVER_PORT + "/api/user/rejectDeletion",request,{headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
        .then(response => {
          this.show('foo-css', 'success',`<p style="font-size: 25px">Successfull!</p>`,`<p style="font-size: 20px">Successfully rejected profile deletion!</p>`)
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

</style>
