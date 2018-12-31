#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

varying LOWP vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;

//RED AREA
uniform float u_curRadius;
uniform vec2 u_curPos;

uniform float u_nextRadius;
uniform vec2 u_nextPos;
uniform float u_raBorderWidth;
uniform int u_keepTexture; // keep texture druing red area drawing

//const float RED_AREA_BORDER = 0.0008;

//uniform sampler2D u_texture1; //empty cell texture
//uniform sampler2D u_texture2; //virus texture

//const vec4 SYSTEM_SKIN_GRAY_COLOR = vec4(vec3(0.9609375), 1.0);
const vec2 CENTER_COORD = vec2(0.5, 0.5);

//SKIN BORDER
const float RADIUS = 0.488;
const float BORDER = 0.02;
const float SOFTNESS = 0.008;
const float eps = 0.03;

// determine cell type by alpha
const float HAS_BORDER = 0.95;
const float RED_AREA = 0.90; // draw red area

//const float EMPTY_CELL = 0.96;
//const float VIRUS_CELL = 0.94;

const float div = 1.5; //offset to adjust image cropping and border width

void drawRedArea() {
//red circle for read rea
    float len, alpha;

    //green circle for safe area
    len = length(u_nextPos - v_texCoords);
    alpha = 1.0 + smoothstep(u_nextRadius, u_nextRadius + u_raBorderWidth, len)
            - smoothstep(u_nextRadius - u_raBorderWidth, u_nextRadius, len);

    if (u_keepTexture == 1) {
        gl_FragColor = texture2D(u_texture, v_texCoords);
    } else {
        gl_FragColor = vec4(1.0, 1.0, 1.0, 0.0);
    }

    gl_FragColor = mix(vec4(0.0, 1.0, 0.0, 0.6), gl_FragColor, alpha);

    //fill red area
    len = length(u_curPos - v_texCoords);
    alpha = step(u_curRadius, len);
    gl_FragColor = mix(gl_FragColor,vec4(1.0, 0.0, 0.0, 0.7),  alpha * 0.7);

    alpha = 1.0 + smoothstep(u_curRadius, u_curRadius + u_raBorderWidth, len)
            - smoothstep(u_curRadius - u_raBorderWidth, u_curRadius, len);

    //red area border
    gl_FragColor = mix(vec4(1.0, 0.0, 0.0, 1.0), gl_FragColor, alpha);

    if (u_keepTexture == 1) {
        gl_FragColor.a = 0.4;
    }
}

void main()
{
    //draw red overlay for Battler Royal
    if (abs(v_color.a - RED_AREA) < eps) {
        drawRedArea();
        return;
    }

    vec4 texColor = texture2D(u_texture, v_texCoords);
    bool needBorder = (abs(v_color.a - HAS_BORDER) < eps);

    if (!needBorder) {
        float len = length(CENTER_COORD - v_texCoords);
        gl_FragColor = (smoothstep(RADIUS+BORDER/div, RADIUS+BORDER/div-SOFTNESS, len)) * texColor * v_color;
    } else { //add border and crop image to circle
        float len = length(CENTER_COORD - v_texCoords);

        //do it 2 times for more rigid edges
        float t = 1.0 + smoothstep(RADIUS, RADIUS+BORDER, len)
                    - smoothstep(RADIUS-BORDER, RADIUS, len);
        t *= 1.0 + smoothstep(RADIUS, RADIUS+BORDER, len)
                    - smoothstep(RADIUS-BORDER, RADIUS, len);

        //add border on pixel with alpha equals t
        texColor = mix(v_color, texColor, t);

        //crop skin to circle
        texColor = (smoothstep(RADIUS+BORDER/div, RADIUS+BORDER/div-SOFTNESS, len)) * texColor;
        gl_FragColor =  texColor;
    }
}