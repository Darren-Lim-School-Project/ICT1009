let mix = require("laravel-mix");
mix.disableNotifications();
mix.setPublicPath("src/main/resources/static/");
mix.setResourceRoot("src/main/resources/static/");

mix.js("src/main/js/app.js", "js/app.js")
   .vue()
   .sass("src/main/sass/app.scss", "css/app.css");

if (mix.inProduction()) {
    mix.version();
}

mix.browserSync({
    proxy: {
        target: "localhost:8080",
        reqHeaders() {
            return {
                host: "localhost:3000",
            };
        },
    },
    notify: false,
    open: false,
    online: false,
    ui: false,
});

mix.options({
    hmrOptions: {
        host: "localhost",
        port: '8081',
    },
});

