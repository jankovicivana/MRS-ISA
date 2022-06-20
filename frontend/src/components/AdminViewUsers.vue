<template>
  <section class="profile_main vh-100" >
    <admin_navbar :isAdmin="true"></admin_navbar>
  <div class="content is-medium" style=" height:80%"  >
    <div class="mask d-flex align-items-center pt-5 h-100 gradient-custom-3"   >
      <div class="container h-100" >
        <div class="row d-flex justify-content-center  h-100"   >
          <div class="col-11 " >
            <div class="card " style="border-radius: 15px; background: #ecd9c6;  height: 100% ">
              <div class="card-body p-5">

                <h1 class="title" v-if="userType === 'clients' ">Clients</h1>
                <h1 class="title" v-if="userType === 'instructors' ">Fishing instructors</h1>
                <h1 class="title" v-if="userType === 'boatOwners' ">Boat owners</h1>
                <h1 class="title" v-if="userType === 'cottageOwners' ">Cottage owners</h1>
                <hr />
                  <div>
                    <table class="table" >
                      <thead>
                      <tr>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th></th>

                      </tr>
                      </thead>
                      <tbody>
                        <tr  v-if="users.length === 0">
                          <td colspan="5" class="p-3">Currently there is no any user.</td>
                        </tr>
                        <tr  v-for="user in users">
                          <td>{{user.name}}</td>
                          <td>{{user.surname}}</td>
                          <td>{{user.email}}</td>
                          <td>{{user.phoneNumber}}</td>
                          <td><button v-on:click="deleteUser(user)" style="background: #2e6b6b; border-radius: 8px;color: #FFFFFF; border-color: #FFFFFF">Delete</button></td>
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
  name: "AdminViewUsers",
  components: {'admin_navbar': AdminNavbar},
  data() {

    return {
      users: '',
      userType:''
    }
  },
  watch:{
    $route(){
      location.reload();
    }
  },
  mounted: function () {
    this.userType = this.$route.params.userType;//.fullPath.substring(this.$route.fullPath.lastIndexOf('/'),this.$route.fullPath.toEnd);
    //this.userType = this.userType.substring(1,this.userType.toEnd)
    console.log(this.userType)
    if (this.userType === "clients") {
      axios
        .get(process.env.VUE_APP_SERVER_PORT + "/api/clients/all", {
          headers: {
            Authorization:
              'Bearer ' + sessionStorage.getItem("accessToken")
          }
        })
        .then(response => (
          this.users = response.data
        ))
    } else if (this.userType === "instructors") {
      axios
        .get(process.env.VUE_APP_SERVER_PORT + "/api/fishingInstructor/all", {
          headers: {
            Authorization:
              'Bearer ' + sessionStorage.getItem("accessToken")
          }
        })
        .then(response => (
          this.users = response.data
        ))


    } else if (this.userType === "boatOwners") {
      axios
        .get(process.env.VUE_APP_SERVER_PORT + "/api/boatOwner/all", {
          headers: {
            Authorization:
              'Bearer ' + sessionStorage.getItem("accessToken")
          }
        })
        .then(response => (
          this.users = response.data
    ))
    } else {
      axios
        .get(process.env.VUE_APP_SERVER_PORT + "/api/cottageOwner/all", {
          headers: {
            Authorization:
              'Bearer ' + sessionStorage.getItem("accessToken")
          }
        })
        .then(response => (
          this.users = response.data
        ))
    }

  },
  methods: {
    show: function (group, type = '', text) {
      let title = `<p style="font-size: 25px">Successful!</p>`
      this.$notify({group, title, text, type})
    },show1: function(group, type='',title,text){
      this.$notify({group, title, text, type})
    },

    deleteUser: function (user) {
      let id = user.id
      if (this.userType === "clients") {
        axios.delete(process.env.VUE_APP_SERVER_PORT + "/api/clients/delete/" + id, {
          headers: {
            Authorization:
              'Bearer ' + sessionStorage.getItem("accessToken")
          }
        })
          .then(response => {
            this.show('foo-css', 'success', `<p style="font-size: 20px">Successfully deleted client!</p>`)
            setTimeout(() => {
              location.reload();
            }, 3000)
            const index = this.users.indexOf(user);
            this.users.splice(index, 1);
          }).catch((error) =>  {
          this.show1('foo-css', 'error',`<p style="font-size: 25px">Warning!</p>`,`<p style="font-size: 20px">Client has some reservations! Deletion is not possible!</p>`)
        });

      } else if (this.userType === "instructors") {
        axios.delete(process.env.VUE_APP_SERVER_PORT + "/api/fishingInstructor/delete/" + id, {
          headers: {
            Authorization:
              'Bearer ' + sessionStorage.getItem("accessToken")
          }
        })
          .then(response => {
            this.show('foo-css', 'success', `<p style="font-size: 20px">Successfully deleted instructor!</p>`)
            setTimeout(() => {
            }, 3000)
            const index = this.users.indexOf(user);
            this.users.splice(index, 1);
          }).catch((error) =>  {
          this.show1('foo-css', 'error',`<p style="font-size: 25px">Warning!</p>`,`<p style="font-size: 20px">Instructor has some reservations! Deletion is not possible!</p>`)
        });
      } else if (this.userType === "boatOwners") {
        axios.delete(process.env.VUE_APP_SERVER_PORT + "/api/boatOwner/delete/" + id, {
          headers: {
            Authorization:
              'Bearer ' + sessionStorage.getItem("accessToken")
          }
        })
          .then(response => {
            this.show('foo-css', 'success', `<p style="font-size: 20px">Successfully deleted boat owner!</p>`)
            setTimeout(() => {
            }, 3000)
            const index = this.users.indexOf(user);
            this.users.splice(index, 1);
          }).catch((error) =>  {
          this.show1('foo-css', 'error',`<p style="font-size: 25px">Warning!</p>`,`<p style="font-size: 20px">Boat owner has some reservations! Deletion is not possible!</p>`)
        });
      } else {
        axios.delete(process.env.VUE_APP_SERVER_PORT + "/api/cottageOwner/delete/" + id, {
          headers: {
            Authorization:
              'Bearer ' + sessionStorage.getItem("accessToken")
          }
        })
          .then(response => {
            this.show('foo-css', 'success', `<p style="font-size: 20px">Successfully deleted cottage owner!</p>`)
            setTimeout(() => {
            }, 3000)
            const index = this.users.indexOf(user);
            this.users.splice(index, 1);
          }).catch((error) =>  {
          this.show1('foo-css', 'error',`<p style="font-size: 25px">Warning!</p>`,`<p style="font-size: 20px">Cottage owner has some reservations! Deletion is not possible!</p>`)
        });     }
    }
  }
}

</script>

<style scoped>

</style>
