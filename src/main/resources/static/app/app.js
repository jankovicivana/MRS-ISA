const PostPage = { template: '<post-page></post-page>'}

const router = new VueRouter({
    mode: 'hash',
    routes: [
        { path:'/',component: PostPage}
    ]
});

var app = new Vue({
    router,
    el: '#rentalApp'
});