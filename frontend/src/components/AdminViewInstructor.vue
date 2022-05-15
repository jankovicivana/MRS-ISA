<template>
  <section class="profile_main vh-100" >

    <div class="content is-medium" style=" height:80%"  >
      <div class="mask d-flex align-items-center pt-5 h-100 gradient-custom-3"   >
        <div class="container h-100" >
          <div class="row d-flex justify-content-center  h-100"   >
            <div class="col-11 " >
              <div class="card " style="border-radius: 15px; background: #ecd9c6;  height: 100% ">
                <div class="card-body p-5">

                  <h1 class="title">Fishing instructors</h1>
                  <hr />
                  <div>
                    <table class="table" >
                      <thead>
                      <tr style="background:#ecd9c6">
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th></th>

                      </tr>
                      </thead>
                      <tbody>
                      <tr style="background: #ecd9c6"  v-if="clients.length == 0">
                        <td colspan="5" class="p-3">Trenutno nema korisnika.</td>
                      </tr>

                      <tr style="background: #ecd9c6" v-for="client in clients">
                        <td>{{client.name}}</td>
                        <td>{{client.surname}}</td>
                        <td>{{client.email}}</td>
                        <td>{{client.phoneNumber}}</td>
                        <td><button v-on:click="deleteClient(client)" style="background: #2e6b6b; border-radius: 8px;color: #FFFFFF; border-color: #FFFFFF">Delete</button></td>
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

export default {
  name: "AdminViewInstructor",
  data(){

    return{
      clients: '',
    }
  },
  mounted:function (){

    axios
      .get(process.env.VUE_APP_SERVER_PORT+"/api/fishingInstructor/all")
      .then(response => (
        this.clients = response.data
      ))


  },
  methods:{
    deleteClient:function (client){
      let id = client.id
      axios.delete(process.env.VUE_APP_SERVER_PORT+"/api/fishingInstructor/delete/"+id)
        .then(response => {
          alert(response.data)
          const index = this.clients.indexOf(client);
          this.clients.splice(index, 1);
        }).catch(function error(error) {
        alert(error.response.data);
      });

    }

  }
}
</script>

<style scoped>

</style>
