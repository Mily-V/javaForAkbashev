module.exports = function (grunt) {

    grunt.initConfig({
        shell: {
            options: {
                stdout: true,
                stderr: true
            },
            server: {
                command: 'java -cp java_and_frontend_project_SDVM-1.0-jar-with-dependencies.jar main.Main 8080'
            }
        },
        fest: {
            templates: {
                files: [{
                    expand: true,
                    cwd: 'templates',
                    src: '*.xml',
                    dest: 'public_html/js/tmpl'
                }],
                options: {
                    template: function (data) {
                        return grunt.template.process(
						  'define(function () { return <%= contents %> ; });',
                            {data: data}
                        );
                    }
                }
            }
        },
		watch: {
            fest: {
                files: ['templates/*.xml'],
                tasks: ['fest'],
                options: {
                    interrupt: true,
                    atBegin: true
                }
            },
			server: {
                files: [
                    'public_html/js/**/*.js', /* ñëåäèì çà ñòàòèêîé */
                    'public_html/css/**/*.css'
                ],
                options: {
                    interrupt: true,
                    livereload: true /* ïåðåçàãðóçèòü ñòðàíèöó */
                }
            },
			sass: {
                files: [
                    'src/css/*.scss'
                ],
				tasks: ['sass']
			}
        },
		sass: {
            style: "compressed",
			css: {
				files: [{
					expand: true,
					cwd: 'src/css', 
					src: '*.scss', 
					dest: 'public/css', 
					ext:  '.css'
				}]
			}
		},
		
		concurrent: {
            target: ['watch', 'shell'],
            options: {
                logConcurrentOutput: true 
            }
        },

        requirejs: {
            build: {
                options: {
                    almond: true,
                    baseUrl: "public_html/js",
                    mainConfigFile: "public_html/js/main.js",
                    name: "main",
                    optimize: "none",
                    out: "public_html/js/build/main.js"
                }
            }
        },
        concat: {
            build: {
                separator: ';\n',
                src: [
                    'public_html/js/lib/almond.js',
                    'public_html/js/build/main.js'
                ],
                dest: 'public_html/js/build.js'
            }
        },
        uglify: {
            build: { /* Подзадача */
                files: {
                    'public_html/js/build.min.js':
                        ['public_html/js/build.js']
                }
            }
        }
    });
	grunt.loadNpmTasks('grunt-contrib-sass');
    grunt.loadNpmTasks('grunt-contrib-watch');
	grunt.loadNpmTasks('grunt-concurrent');
    grunt.loadNpmTasks('grunt-shell');
    grunt.loadNpmTasks('grunt-fest');
    grunt.loadNpmTasks('grunt-contrib-requirejs');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-concat');

    grunt.registerTask(
        'build',
        [
            'fest', 'requirejs:build',
            'concat:build', 'uglify:build'
        ]
    );
    grunt.registerTask('default', ['concurrent']);
	//grunt.registerTask('default', ['shell', 'watch']);	

};
