const PROXY_CONFIG = [
    {
        context: ['/ag'], //tudo que for /ag será uma chamada para o backend
        target: '"http://localhost:8080"/',
        secure: false,
        logLevel: "debug",
        pathRewrite: {'^/ag': ''}
    }
];

module.exports = PROXY_CONFIG;

//https://youtu.be/D9oFe6rHjpY?si=6mjbyrsr4_ZQ3l65

// para usá-lo execute com npm run start

//Está é uma forma de configurar o cross só aqui sem precisar
// config no backend, mas não funcionou, se quiser tentar em outra app
// basta ver o vídeo