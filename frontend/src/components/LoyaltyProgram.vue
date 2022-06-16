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

                  <h3 class="title">Loyalty program</h3>
                  <hr />
                  <div>
                    <div style="background-color: antiquewhite" class="container rounded mt-1 " id = "loyalty_container">
                      <div class="row col-9" style="display: inline-block;">
                        <div class="col-md-12 border-right">
                          <div class="p-3 py-3">

                            <div class="row mt-3 pt-2" style="background-color: #e3c7aa">
                              <label class="mb-lg-3 col-3" style="padding-left: 5px;font-size: 20px; ">Rang</label>
                              <label class="mb-lg-3 col-3" style="padding-left: 5px; ">Point limit</label>
                              <label class="mb-lg-3 col-3" style="padding-left: 5px; ">Client discount</label>
                              <label class="mb-lg-3 col-3" style="padding-left: 5px; ">Advertiser benefit</label>
                            </div>
                            <div class="row mt-4 mb-5">
                              <div class="col-3" style="color: #2e6b6b;  ">&#127941;  REGULAR</div>
                              <div class="col-3 align-content-center">
                                <span class="col-1 input_loyalty"><label  style="background-color: antiquewhite" /></span>
                                <span class="col-2 "><  </span>
                                <span class="col-5 input_loyalty"><input type="text" class="form-control" placeholder="to"  v-model="regular.pointsLimit"></span>
                              </div>
                              <div class="col-3">
                                <span class="col-10 input_loyalty"><input type="text" class="form-control" placeholder="discount"  v-model="regular.clientDiscount"></span>
                              </div>
                              <div class="col-3">
                                <span class="col-10 input_loyalty"><input type="text" class="form-control" placeholder="benefit"  v-model="regular.advertiserBenefits"></span>
                              </div>

                            </div>
                              <div class="row mt-4 mb-5">
                              <div class="col-2" style="color: slategrey;  ">&#127941;  SILVER</div>
                                <div class="col-4 align-content-center">
                                    <span class="col-4 input_loyalty ml-1"><input  type="text" class="form-control" placeholder="from"  v-model="regular.pointsLimit"></span>
                                    <span class="col-2 ">-</span>
                                    <span class="col-4 input_loyalty"><input type="text" class="form-control" placeholder="to"  v-model="silver.pointsLimit"></span>
                                </div>
                                <div class="col-3">
                                  <span class="col-10 input_loyalty"><input type="text" class="form-control" placeholder="discount"  v-model="silver.clientDiscount"></span>
                                </div>
                                <div class="col-3">
                                  <span class="col-10 input_loyalty"><input type="text" class="form-control" placeholder="benefit"  v-model="silver.advertiserBenefits"></span>
                                </div>

                                </div>

                            <div class="row mt-2">

                             <span class="col-3" style="color: gold;">&#127941;  GOLD</span>
                              <div class="col-3 align-content-center">
                                <span class="col-1 input_loyalty"><label  style="background-color: antiquewhite" /></span>
                                <span class="col-2 ">>  </span>
                                <span class="col-5 input_loyalty"><input type="text" class="form-control" placeholder="to"  v-model="silver.pointsLimit"></span>
                              </div>
                              <div class="col-3">
                                <span class="col-10 input_loyalty"><input type="text" class="form-control" placeholder="discount"  v-model="gold.clientDiscount"></span>
                              </div>
                              <div class="col-3">
                                <span class="col-10 input_loyalty"><input type="text" class="form-control" placeholder="benefit"  v-model="gold.advertiserBenefits"></span>
                              </div>
                            </div>

                            <div style="float: right;margin-right: 30px"  class="mt-3 text-right"><button v-on:click="edit()"  id="editButton" class="btn btn-primary edit-button" type="button">Save</button></div>
                          </div>
                        </div>


                          </div>
                      <div class="col-3 " style="float: right;font-size: 14px;padding-top: 120px;padding-right: 10px;padding-left: 30px">
                        <div><div>&#10067 <b>Client discount </b></div>
                        Discount in percents which client gets on every reservation</div>

                        <div style="padding-top: 60px"><div>&#10067 <b>Advertiser benefit </b></div>
                        Bonus profit which advertiser gets of every reservation </div>
                      </div>
                        </div>
                      </div>

                    </div>
                  <div class="card-body p-5">

                  <h3 class="title">System info</h3>
                  <hr />

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
  name: "LoyaltyProgram",
  components: {AdminNavbar},
  data(){
    return{
      requests:'',
      client : '',
      address:'',
      regular:'',
      silver:'',
      gold:''
    }
  },
  mounted() {
    axios
      .get(process.env.VUE_APP_SERVER_PORT+"/api/system/getRankingInfo/1", {headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
      .then(response => (
        this.regular = response.data

      ));
    axios
      .get(process.env.VUE_APP_SERVER_PORT+"/api/system/getRankingInfo/2", {headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
      .then(response => (
        this.silver = response.data

      ));

    axios
      .get(process.env.VUE_APP_SERVER_PORT+"/api/system/getRankingInfo/3", {headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
      .then(response => (
        this.gold = response.data

      ));
  },
  methods:{
    show: function(group, type='',title,text){
      this.$notify({group, title, text, type})
    },

    edit:function (){
      this.gold.pointsLimit = this.silver.pointsLimit;
     axios
        .post(process.env.VUE_APP_SERVER_PORT+"/api/system/save",this.regular, {headers: {Authorization:
              'Bearer ' + sessionStorage.getItem("accessToken")}})

      axios
        .post(process.env.VUE_APP_SERVER_PORT+"/api/system/save",this.silver, {headers: {Authorization:
              'Bearer ' + sessionStorage.getItem("accessToken")}})

      axios
        .post(process.env.VUE_APP_SERVER_PORT+"/api/system/save",this.gold, {headers: {Authorization:
              'Bearer ' + sessionStorage.getItem("accessToken")}})
        .then(response =>{
          this.show('foo-css', 'success',`<p style="font-size: 25px">Successfull!</p>`,`<p style="font-size: 20px">Successfully saved loyalty program info!</p>`)
          setTimeout(() => {}, 3000)
        });
    }
  }
}
</script>

<style scoped>

</style>
