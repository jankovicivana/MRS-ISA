const PostPage = { template: '<post-page></post-page>'}

const ClientProfile = {template: '<client-profile></client-profile>'}

const router = new VueRouter({
    mode: 'hash',
    routes: [
        { path:'/',component: PostPage},
        { path: '/clientProfile', component: ClientProfile}
    ]
});

var app = new Vue({
    router,
    el: '#rentalApp'
});