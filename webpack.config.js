const path = require('path')

module.exports = {
    stats: 'errors-warnings',
    resolve: {
        alias: {
            '@': path.resolve('./src/main/js'),
        },
    },
}
