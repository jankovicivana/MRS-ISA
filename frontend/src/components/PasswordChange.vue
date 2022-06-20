<template>
      <section class="vh-100" style="background-image: url('https://images.unsplash.com/photo-1418065460487-3e41a6c84dc5?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80'); background-repeat: no-repeat;
  background-attachment: fixed;
  background-size: cover;">
        <div class="mask  align-items-center pt-3 gradient-custom-3">
          <div class="container" style="margin-top: 150px" >
            <div class="row justify-content-center align-items-center m-5" >
              <div class="col-12 col-md-9 col-lg-7 col-xl-6" >
                <div class="card" style="border-radius: 15px; background: #ecd9c6;width: 1000px">
                  <div class="card-body p-5">
                    <h2 class="text-uppercase text-center mb-5" style=" color: #04414d; font-family: 'Bookman Old Style',serif;">Password change</h2>
                    <form>
                      <hr/>

                      <div class="form-check pb-3">
                        <label style="width: 150px" class="form-check-label" for="flexRadioDefault2">
                          New password
                        </label>
                        <input class="ml-4" ref="pass1" style="height: 35px"  type="password" name="flexRadioDefault" id="flexRadioDefault2">

                      </div>
                      <div class="form-check pb-3">
                        <label style="width: 150px" class="form-check-label" for="flexRadioDefault1">
                          Confirm password
                        </label>
                        <input class="ml-4" ref="pass2" style="height: 35px"  type="password" name="flexRadioDefault" id="flexRadioDefault1">

                      </div>


                      <div class="d-flex justify-content-center">
                        <button type="button" v-on:click="change()" class="btn btn-success btn-block  gradient-custom-4 text-body " style="background-color: #04414d;margin-left: 170px"><span style="color:white">Change</span></button>
                      </div>
                    </form>
                    <br/>

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
import router from "../router";

export default {
  name: "PasswordChange",
  data: function (){
    return{
      clientId: ''
    }
  },methods:{
    show: function(group, type='',title,text){
      this.$notify({group, title, text, type})
    },change:function (){
      let pass1 = this.$refs.pass1.value
      let pass2 = this.$refs.pass2.value

      if (pass1!==pass2){
        this.show('foo-css', 'error',`<p style="font-size: 25px">Warning!</p>`,`<p style="font-size: 20px">Passwords don't match!</p>`)
        return
      }

      if (pass1===''){
        this.show('foo-css', 'error',`<p style="font-size: 25px">Warning!</p>`,`<p style="font-size: 20px">You must enter password!</p>`)
        return;
      }
      axios
        .post(process.env.VUE_APP_SERVER_PORT+"/api/administrator/changePassword",pass1, {headers: {Authorization:
              'Bearer ' + sessionStorage.getItem("accessToken")}}).then(response =>{
        this.show('foo-css', 'success',`<p style="font-size: 25px">Successfull!</p>`,`<p style="font-size: 20px">Successfully changed password!</p>`)
        setTimeout(() => {}, 3000)
        router.push('/admin/homepage');
      });


    }





  }
}
</script>

<style scoped>

</style>
