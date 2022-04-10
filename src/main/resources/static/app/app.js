const PostPage = { template: '<post-page></post-page>'}
const cottageProfile = { template: '<cottage-profile></cottage-profile>'}

const router = new VueRouter({
    mode: 'hash',
    routes: [
        { path:'/',component: PostPage},
        { path: '/cottageProfile',component: cottageProfile}
    ]
});

var app = new Vue({
    router,
    el: '#rentalApp'
});