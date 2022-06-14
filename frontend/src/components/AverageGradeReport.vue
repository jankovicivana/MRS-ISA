<template>
  <section class="basic_background vh-100" >
    <fishing-instructor-navbar v-if="role === 'ROLE_fishingInstructor'"></fishing-instructor-navbar>
    <cottage-owner-navbar v-if="role === 'ROLE_cottageOwner'"></cottage-owner-navbar>
    <boat-owner-navbar v-if="role === 'ROLE_boatOwner'"></boat-owner-navbar>
    <div class="content is-medium" style=" height:80%"  >
      <div class="mask d-flex align-items-center pt-5 h-100 gradient-custom-3"   >
        <div class="container h-100" >
          <div class="row d-flex justify-content-center  h-100"   >
            <div class="col-11 " >
              <div class="card " style="border-radius: 15px; background: #ecd9c6;  height: 100% ">

                <div class="card-body p-5">

                  <h3 class="title">Average grade report</h3>
                  <hr />
                  <div>
                    <table class="table">
                      <thead>
                      <tr >

                        <th colspan="2">Adventure</th>
                        <th colspan="2">Average grade</th>

                      </tr>
                      </thead>
                      <tbody>
                      <tr  v-if="entities.length == 0">
                        <td colspan="9" class="p-3">There isn't any entity.</td>
                      </tr>
                      <tr  v-for="r in entities">
                        <td colspan="2">{{r.name}}</td>
                        <td colspan="2">{{r.averageGrade}}</td>

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
import FishingInstructorNavbar from "./header/FishingInstructorNavbar";
import axios from "axios";
import CottageOwnerNavbar from "./header/CottageOwnerNavbar";
import BoatOwnerNavbar from "./header/BoatOwnerNavbar";
export default {
  name: "AverageGradeReport",
  components: {BoatOwnerNavbar, CottageOwnerNavbar, FishingInstructorNavbar},
  data(){
    return{
      role:'',
      entities: []
    }
  }
  ,mounted:function () {
    this.role = sessionStorage.getItem("role");
    let url = '';
    if (this.role === "ROLE_fishingInstructor") {
      url = "/api/adventures/allByOwner";
     }else if(this.role === "ROLE_boatOwner"){
      url = "/api/boats/allByOwner";
     }else if (this.role === "ROLE_cottageOwner"){
      url = "/api/cottages/allByOwner";
    }
    axios
      .get(process.env.VUE_APP_SERVER_PORT + url, {
        headers: {
          Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")
        }
      })
      .then(response => {
        this.entities = response.data
      })

  },
  methods:{}
}
</script>

<style scoped>

</style>
