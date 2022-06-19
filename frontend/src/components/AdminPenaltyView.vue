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

                  <h3 class="title">Client penalty approval</h3>
                  <hr />
                  <div>
                    <table class="table">
                      <thead>
                      <tr>

                        <th>Client</th>
                        <th>Advertiser</th>
                        <th>Report</th>
                        <th></th>
                        <th></th>
                        <th></th>
                        <th></th>

                      </tr>
                      </thead>
                      <tbody>
                      <tr   v-if="reports.length == 0">
                        <td colspan="7" class="p-3">There is no penalty report.</td>
                      </tr>
                      <tr v-for="r in reports">
                        <td>{{r.client.surname + " "+ r.client.name}}</td>
                        <td>{{r.advertiser.surname + " "+ r.advertiser.name}}</td>
                        <td colspan="2">{{r.reportString}}</td>
                        <td></td>

                        <td><button v-on:click="acceptPenalty(r)" style="background: #2e6b6b; border-radius: 8px;color: #FFFFFF; border-color: #FFFFFF">Accept</button></td>
                        <td><button v-on:click="rejectPenalty(r)" style="background: darkred; border-radius: 8px;color: #FFFFFF; border-color: #FFFFFF">Reject</button></td>
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
  name: "AdminPenaltyView",
  components: {AdminNavbar},
  data(){

    return{
      reports: ''
    }
  }
  ,mounted:function (){
    axios
      .get(process.env.VUE_APP_SERVER_PORT+"/api/clients/getReportOnHold", {headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
      .then(response => (
        this.reports = response.data
      ));


  },
  methods: {
    show: function(group, type='',title,text){
      this.$notify({group, title, text, type})
    },

    acceptPenalty:function (report){
      let id = report.id
      axios.get(process.env.VUE_APP_SERVER_PORT + "/api/clients/acceptPenalty/"+id,{headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
        .then(response => {
          this.show('foo-css', 'success',`<p style="font-size: 25px">Successfull!</p>`,`<p style="font-size: 20px">Successfully accepted penalty!</p>`)
          setTimeout(() => {
            location.reload();
          }, 3000)
          const index = this.reports.indexOf(report);
          this.reports.splice(index, 1);
        }).catch((error) =>  {
        this.show('foo-css', 'warning',`<p style="font-size: 25px">Warning!</p>`,`<p style="font-size: 20px">Another admin answered this request!</p>`)
      });
    },
    rejectPenalty:function (report){
      let id = report.id
      axios.get(process.env.VUE_APP_SERVER_PORT + "/api/clients/rejectPenalty/"+id,{headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
        .then(response => {
          this.show('foo-css', 'success',`<p style="font-size: 25px">Successfull!</p>`,`<p style="font-size: 20px">Successfully rejected penalty!</p>`)
          setTimeout(() => {
            location.reload();
          }, 3000)
          const index = this.reports.indexOf(report);
          this.reports.splice(index, 1);
        }).catch((error) =>  {
        this.show('foo-css', 'warning',`<p style="font-size: 25px">Warning!</p>`,`<p style="font-size: 20px">Another admin answered this request!</p>`)
      });
    }

  }

  }
</script>

<style scoped>

</style>
