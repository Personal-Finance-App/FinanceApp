package financeapp.category;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InitializeCategory {
    private final CategoryService categoryService;

    private static final HashMap<String, List<String>> data = new HashMap<>() {{
        put(CategoriesList.AVIABILETY.label,new ArrayList<>() {{
            add("3000");
            add("3001");
            add("3002");
            add("3003");
            add("3004");
            add("3005");
            add("3006");
            add("3007");
            add("3008");
            add("3009");
            add("3010");
            add("3011");
            add("3012");
            add("3013");
            add("3014");
            add("3015");
            add("3016");
            add("3017");
            add("3018");
            add("3019");
            add("3020");
            add("3021");
            add("3022");
            add("3023");
            add("3024");
            add("3025");
            add("3026");
            add("3027");
            add("3028");
            add("3029");
            add("3030");
            add("3031");
            add("3032");
            add("3033");
            add("3034");
            add("3035");
            add("3036");
            add("3037");
            add("3038");
            add("3039");
            add("3040");
            add("3041");
            add("3042");
            add("3043");
            add("3044");
            add("3045");
            add("3046");
            add("3047");
            add("3048");
            add("3049");
            add("3050");
            add("3051");
            add("3052");
            add("3053");
            add("3054");
            add("3055");
            add("3056");
            add("3057");
            add("3058");
            add("3059");
            add("3060");
            add("3061");
            add("3062");
            add("3063");
            add("3064");
            add("3065");
            add("3066");
            add("3067");
            add("3068");
            add("3069");
            add("3070");
            add("3071");
            add("3072");
            add("3073");
            add("3074");
            add("3075");
            add("3076");
            add("3077");
            add("3078");
            add("3079");
            add("3080");
            add("3081");
            add("3082");
            add("3083");
            add("3084");
            add("3085");
            add("3086");
            add("3087");
            add("3088");
            add("3089");
            add("3090");
            add("3091");
            add("3092");
            add("3093");
            add("3094");
            add("3095");
            add("3096");
            add("3097");
            add("3098");
            add("3099");
            add("3100");
            add("3101");
            add("3102");
            add("3103");
            add("3104");
            add("3105");
            add("3106");
            add("3107");
            add("3108");
            add("3109");
            add("3110");
            add("3111");
            add("3112");
            add("3113");
            add("3114");
            add("3115");
            add("3116");
            add("3117");
            add("3118");
            add("3119");
            add("3120");
            add("3121");
            add("3122");
            add("3123");
            add("3124");
            add("3125");
            add("3126");
            add("3127");
            add("3128");
            add("3129");
            add("3130");
            add("3131");
            add("3132");
            add("3133");
            add("3134");
            add("3135");
            add("3136");
            add("3137");
            add("3138");
            add("3139");
            add("3140");
            add("3141");
            add("3142");
            add("3143");
            add("3144");
            add("3145");
            add("3146");
            add("3147");
            add("3148");
            add("3149");
            add("3150");
            add("3151");
            add("3152");
            add("3153");
            add("3154");
            add("3155");
            add("3156");
            add("3157");
            add("3158");
            add("3159");
            add("3160");
            add("3161");
            add("3162");
            add("3163");
            add("3164");
            add("3165");
            add("3166");
            add("3167");
            add("3168");
            add("3169");
            add("3170");
            add("3171");
            add("3172");
            add("3173");
            add("3174");
            add("3175");
            add("3176");
            add("3177");
            add("3178");
            add("3179");
            add("3180");
            add("3181");
            add("3182");
            add("3183");
            add("3184");
            add("3185");
            add("3186");
            add("3187");
            add("3188");
            add("3189");
            add("3190");
            add("3191");
            add("3192");
            add("3193");
            add("3194");
            add("3195");
            add("3196");
            add("3197");
            add("3198");
            add("3199");
            add("3200");
            add("3201");
            add("3202");
            add("3203");
            add("3204");
            add("3205");
            add("3206");
            add("3207");
            add("3208");
            add("3209");
            add("3210");
            add("3211");
            add("3212");
            add("3213");
            add("3214");
            add("3215");
            add("3216");
            add("3217");
            add("3218");
            add("3219");
            add("3220");
            add("3221");
            add("3222");
            add("3223");
            add("3224");
            add("3225");
            add("3226");
            add("3227");
            add("3228");
            add("3229");
            add("3230");
            add("3231");
            add("3232");
            add("3233");
            add("3234");
            add("3235");
            add("3236");
            add("3237");
            add("3238");
            add("3239");
            add("3240");
            add("3241");
            add("3242");
            add("3243");
            add("3244");
            add("3245");
            add("3246");
            add("3247");
            add("3248");
            add("3249");
            add("3250");
            add("3251");
            add("3252");
            add("3253");
            add("3254");
            add("3255");
            add("3256");
            add("3257");
            add("3258");
            add("3259");
            add("3260");
            add("3261");
            add("3262");
            add("3263");
            add("3264");
            add("3265");
            add("3266");
            add("3267");
            add("3268");
            add("3269");
            add("3270");
            add("3271");
            add("3272");
            add("3273");
            add("3274");
            add("3275");
            add("3276");
            add("3277");
            add("3278");
            add("3279");
            add("3280");
            add("3281");
            add("3282");
            add("3283");
            add("3284");
            add("3285");
            add("3286");
            add("3287");
            add("3288");
            add("3289");
            add("3290");
            add("3291");
            add("3292");
            add("3293");
            add("3294");
            add("3295");
            add("3296");
            add("3297");
            add("3298");
            add("3299");
            add("3300");
            add("3301");
            add("3302");
            add("3303");
            add("3304");
            add("3305");
            add("3306");
            add("3307");
            add("3308");
            add("3309");
            add("3310");
            add("3311");
            add("3312");
            add("3313");
            add("3314");
            add("3315");
            add("3316");
            add("3317");
            add("3318");
            add("3319");
            add("3320");
            add("3321");
            add("3322");
            add("3323");
            add("3324");
            add("3325");
            add("3326");
            add("3327");
            add("3328");
            add("3329");
            add("3330");
            add("3331");
            add("3332");
            add("3333");
            add("3334");
            add("3335");
            add("3336");
            add("3337");
            add("3338");
            add("3339");
            add("3340");
            add("3341");
            add("3342");
            add("3343");
            add("3344");
            add("3345");
            add("3346");
            add("3347");
            add("3348");
            add("3349");
            add("3350");
            add("4418 ");
            add("4304 ");
            add("4511 ");
            add("4415 ");
            add("4582");
        }});
        put(CategoriesList.APTEKI.label,new ArrayList<>() {{
            add("5122");
            add("5295");
            add("5292");
            add("5912");
        }});
        put(CategoriesList.ARENDA_AVTO.label,new ArrayList<>() {{
            add("3351");
            add("3352");
            add("3353");
            add("3354");
            add("3355");
            add("3356");
            add("3357");
            add("3358");
            add("3359");
            add("3360");
            add("3361");
            add("3362");
            add("3363");
            add("3364");
            add("3365");
            add("3366");
            add("3367");
            add("3368");
            add("3369");
            add("3370");
            add("3371");
            add("3372");
            add("3373");
            add("3374");
            add("3375");
            add("3376");
            add("3377");
            add("3378");
            add("3379");
            add("3380");
            add("3381");
            add("3382");
            add("3383");
            add("3384");
            add("3385");
            add("3386");
            add("3387");
            add("3388");
            add("3389");
            add("3390");
            add("3391");
            add("3392");
            add("3393");
            add("3394");
            add("3395");
            add("3396");
            add("3397");
            add("3398");
            add("3400");
            add("3401");
            add("3402");
            add("3403");
            add("3404");
            add("3405");
            add("3406");
            add("3407");
            add("3408");
            add("3409");
            add("3410");
            add("3411");
            add("3412");
            add("3413");
            add("3414");
            add("3415");
            add("3416");
            add("3417");
            add("3418");
            add("3419");
            add("3420");
            add("3421");
            add("3422");
            add("3423");
            add("3424");
            add("3425");
            add("3426");
            add("3427");
            add("3428");
            add("3429");
            add("3430");
            add("3431");
            add("3432");
            add("3433");
            add("3434");
            add("3435");
            add("3436");
            add("3437");
            add("3438");
            add("3439");
            add("3441");
            add("7519");
            add("7512");
        }});
        put(CategoriesList.DOM_REMONT.label,new ArrayList<>() {{
            add("1520");
            add("1771");
            add("5046");
            add("1711");
            add("1799");
            add("5051");
            add("1731");
            add("2791");
            add("5065");
            add("1740");
            add("2842");
            add("5072");
            add("1750");
            add("5021");
            add("5074");
            add("1761");
            add("5039");
            add("5085");
            add("5198");
            add("5415");
            add("7623");
            add("5200");
            add("5712");
            add("5713");
            add("5714");
            add("7629");
            add("5211");
            add("5718");
            add("7641");
            add("5231");
            add("5719");
            add("7692");
            add("5251");
            add("5722");
            add("7699");
            add("5261");
            add("7622");
        }});
        put(CategoriesList.ZHD_BILETY.label,new ArrayList<>() {{
            add("4011");
            add("4112");
        }});
        put(CategoriesList.KINO.label,new ArrayList<>() {{
            add("7829");
            add("7832");
            add("7841");
        }});
        put(CategoriesList.KRASOTA_.label,new ArrayList<>() {{
            add("5977");
            add("7230");
            add("7297");
            add("7998");
        }});
        put(CategoriesList.ZHIVOTNYE.label,new ArrayList<>() {{
            add("0742");
            add("5995");
        }});
        put(CategoriesList.NALICHNYE.label,new ArrayList<>() {{
            add("6010");
            add("6011");
        }});
        put(CategoriesList.ISKUSSTVO.label,new ArrayList<>() {{
            add("5932");
            add("5937");
            add("5970");
            add("5771");
            add("5972");
            add("5973");
        }});
        put(CategoriesList.NEKOMMERCHESKIE_ORGANIZATSII.label,new ArrayList<>() {{
            add("8398");
            add("8675");
            add("8931");
            add("8641");
            add("8699");
            add("8999");
            add("8651");
            add("8734");
            add("8661");
            add("8911");
        }});
        put(CategoriesList.OBRAZOVANIE.label,new ArrayList<>() {{
            add("8211");
            add("8249");
            add("8220");
            add("8299");
            add("8241");
            add("8493");
            add("8244");
            add("8494");
        }});
        put(CategoriesList.PLATEZHI_V_BJUDZHET.label,new ArrayList<>() {{
            add("9211");
            add("9402");
            add("9222");
            add("9405");
            add("9223");
            add("9751");
            add("9311");
            add("9312");
            add("9313");
            add("9314");
            add("9752");
            add("9399");
            add("9950");
        }});
        put(CategoriesList.SVJAZ_TELEKOM.label,new ArrayList<>() {{
            add("4812");
            add("4813");
            add("4814");
            add("4815");
            add("4816");
            add("4901");
            add("7479");
            add("4821");
            add("4902");
            add("7894");
            add("4829");
            add("7372");
            add("4896");
            add("4897");
            add("4898");
            add("4899");
            add("7375");
        }});
        put(CategoriesList.RAZVLECHENIJA.label,new ArrayList<>() {{
            add("7911");
            add("7941");
            add("7922");
            add("7991");
            add("7992");
            add("7993");
            add("7994");
            add("7929");
            add("7996");
            add("7997");
            add("7998");
            add("7999");
            add("7932");
            add("8664");
            add("7933");
        }});
        put(CategoriesList.TOPLIVO.label,new ArrayList<>() {{
            add("5172");
            add("5542");
            add("5541");
            add("5983");
        }});
        put(CategoriesList.TRANSPORT.label,new ArrayList<>() {{
            add("0011");
            add("4784");
            add("5592");
            add("4111");
            add("4789");
            add("5598");
            add("4121");
            add("5013");
            add("5599");
            add("4131");
            add("5271");
            add("7511");
            add("4457");
            add("5551");
            add("7523");
            add("4468");
            add("5561");
        }});
        put(CategoriesList.TURAGENTSTVA.label,new ArrayList<>() {{
            add("4411");
            add("4722");
            add("4416");
            add("4723");
            add("4417");
            add("4761");
            add("4419");
            add("7015");
        }});
        put(CategoriesList.TSVETY.label,new ArrayList<>() {{
            add("5193 ");
            add("5992 ");
        }});
        put(CategoriesList.CHASTNYE_USLUGI.label,new ArrayList<>() {{
            add("7014");
            add("7277");
            add("7261");
            add("7295");
            add("7273");
            add("7299");
            add("7276");
        }});
        put(CategoriesList.MEDITSINSKIE_USLUGI.label,new ArrayList<>() {{
            add("4119");
            add("5976");
            add("5047");
            add("8011");
            add("5296");
            add("8021");
            add("5975");
            add("8031");
            add("8041");
            add("8042");
            add("8043");
            add("8044");
            add("8071");
            add("8049");
            add("8099");
            add("8050");
            add("8351");
            add("8062");
            add("8676");
        }});
        put(CategoriesList.MUZYKA.label,new ArrayList<>() {{
            add("5733");
            add("5735");
        }});
        put(CategoriesList.ODEZHDA_I_OBUV.label,new ArrayList<>() {{
            add("5094");
            add("5621");
            add("5661");
            add("5931");
            add("7196");
            add("5137");
            add("5631");
            add("5681");
            add("5944");
            add("7631");
            add("5139");
            add("5641");
            add("5691");
            add("5949");
            add("5611");
            add("5651");
            add("5697");
            add("5698");
            add("5699");
            add("5950");
        }});
        put(CategoriesList.OTELI.label,new ArrayList<>() {{
            add("3501");
            add("3502");
            add("3503");
            add("3504");
            add("3505");
            add("3506");
            add("3507");
            add("3508");
            add("3509");
            add("3510");
            add("3511");
            add("3512");
            add("3513");
            add("3514");
            add("3515");
            add("3516");
            add("3517");
            add("3518");
            add("3519");
            add("3520");
            add("3521");
            add("3522");
            add("3523");
            add("3524");
            add("3525");
            add("3526");
            add("3527");
            add("3528");
            add("3529");
            add("3530");
            add("3531");
            add("3532");
            add("3533");
            add("3534");
            add("3535");
            add("3536");
            add("3537");
            add("3538");
            add("3539");
            add("3540");
            add("3541");
            add("3542");
            add("3543");
            add("3544");
            add("3545");
            add("3546");
            add("3547");
            add("3548");
            add("3549");
            add("3550");
            add("3551");
            add("3552");
            add("3553");
            add("3554");
            add("3555");
            add("3556");
            add("3557");
            add("3558");
            add("3559");
            add("3560");
            add("3561");
            add("3562");
            add("3563");
            add("3564");
            add("3565");
            add("3566");
            add("3567");
            add("3568");
            add("3569");
            add("3570");
            add("3571");
            add("3572");
            add("3573");
            add("3574");
            add("3575");
            add("3576");
            add("3577");
            add("3578");
            add("3579");
            add("3580");
            add("3581");
            add("3582");
            add("3583");
            add("3584");
            add("3585");
            add("3586");
            add("3587");
            add("3588");
            add("3589");
            add("3590");
            add("3591");
            add("3592");
            add("3593");
            add("3594");
            add("3595");
            add("3596");
            add("3597");
            add("3598");
            add("3599");
            add("3600");
            add("3601");
            add("3602");
            add("3603");
            add("3604");
            add("3605");
            add("3606");
            add("3607");
            add("3608");
            add("3609");
            add("3610");
            add("3611");
            add("3612");
            add("3613");
            add("3614");
            add("3615");
            add("3616");
            add("3617");
            add("3618");
            add("3619");
            add("3620");
            add("3621");
            add("3622");
            add("3623");
            add("3624");
            add("3625");
            add("3626");
            add("3627");
            add("3628");
            add("3629");
            add("3630");
            add("3631");
            add("3632");
            add("3633");
            add("3634");
            add("3635");
            add("3636");
            add("3637");
            add("3638");
            add("3639");
            add("3640");
            add("3641");
            add("3642");
            add("3643");
            add("3644");
            add("3645");
            add("3646");
            add("3647");
            add("3648");
            add("3649");
            add("3650");
            add("3651");
            add("3652");
            add("3653");
            add("3654");
            add("3655");
            add("3656");
            add("3657");
            add("3658");
            add("3659");
            add("3660");
            add("3661");
            add("3662");
            add("3663");
            add("3664");
            add("3665");
            add("3666");
            add("3667");
            add("3668");
            add("3669");
            add("3670");
            add("3671");
            add("3672");
            add("3673");
            add("3674");
            add("3675");
            add("3676");
            add("3677");
            add("3678");
            add("3679");
            add("3680");
            add("3681");
            add("3682");
            add("3683");
            add("3684");
            add("3685");
            add("3686");
            add("3687");
            add("3688");
            add("3689");
            add("3690");
            add("3691");
            add("3692");
            add("3693");
            add("3694");
            add("3695");
            add("3696");
            add("3697");
            add("3698");
            add("3699");
            add("3700");
            add("3701");
            add("3702");
            add("3703");
            add("3704");
            add("3705");
            add("3706");
            add("3707");
            add("3708");
            add("3709");
            add("3710");
            add("3711");
            add("3712");
            add("3713");
            add("3714");
            add("3715");
            add("3716");
            add("3717");
            add("3718");
            add("3719");
            add("3720");
            add("3721");
            add("3722");
            add("3723");
            add("3724");
            add("3725");
            add("3726");
            add("3727");
            add("3728");
            add("3729");
            add("3730");
            add("3731");
            add("3732");
            add("3733");
            add("3734");
            add("3735");
            add("3736");
            add("3737");
            add("3738");
            add("3739");
            add("3740");
            add("3741");
            add("3742");
            add("3743");
            add("3744");
            add("3745");
            add("3746");
            add("3747");
            add("3748");
            add("3749");
            add("3750");
            add("3751");
            add("3752");
            add("3753");
            add("3754");
            add("3755");
            add("3756");
            add("3757");
            add("3758");
            add("3759");
            add("3760");
            add("3761");
            add("3762");
            add("3763");
            add("3764");
            add("3765");
            add("3766");
            add("3767");
            add("3768");
            add("3769");
            add("3770");
            add("3771");
            add("3772");
            add("3773");
            add("3774");
            add("3775");
            add("3776");
            add("3777");
            add("3778");
            add("3779");
            add("3780");
            add("3781");
            add("3782");
            add("3783");
            add("3784");
            add("3785");
            add("3786");
            add("3787");
            add("3788");
            add("3789");
            add("3790");
            add("3791");
            add("3792");
            add("3793");
            add("3794");
            add("3795");
            add("3796");
            add("3797");
            add("3798");
            add("3799");
            add("3800");
            add("3801");
            add("3802");
            add("3803");
            add("3804");
            add("3805");
            add("3806");
            add("3807");
            add("3808");
            add("3809");
            add("3810");
            add("3811");
            add("3812");
            add("3813");
            add("3814");
            add("3815");
            add("3816");
            add("3817");
            add("3818");
            add("3819");
            add("3820");
            add("3821");
            add("3822");
            add("3823");
            add("3824");
            add("3825");
            add("3826");
            add("3827");
            add("7011");
            add("7032");
            add("7033");
        }});
        put(CategoriesList.DUTY_FREE.label,new ArrayList<>() {{
            add("5309");
        }});
        put(CategoriesList.FASTFUD.label,new ArrayList<>() {{
            add("5814");
        }});
        put(CategoriesList.FINANSOVYE_USLUGI.label,new ArrayList<>() {{
            add("5416");
            add("6051");
            add("6399");
            add("6611");
            add("5417");
            add("6211");
            add("6513");
            add("6760");
            add("6012");
            add("6300");
            add("6529");
            add("6530");
            add("6531");
            add("6532");
            add("6533");
            add("6534");
            add("6535");
            add("6536");
            add("6537");
            add("6538");
            add("7322");
            add("6050");
            add("6381");
            add("6540");
            add("9411");
        }});
        put(CategoriesList.FOTOVIDEO.label,new ArrayList<>() {{
            add("5544");
            add("7333");
            add("5045");
            add("7338");
            add("5946");
            add("7339");
            add("7332");
            add("7395");
        }});
        put(CategoriesList.RAZNYE_TOVARY.label,new ArrayList<>() {{
            add("5099");
            add("5311");
            add("5131");
            add("5331");
            add("5169");
            add("5339");
            add("5310");
            add("5732");
            add("5734");
            add("5945");
            add("5996");
            add("5997");
            add("5998");
            add("5999");
            add("5933");
            add("5948");
            add("7278");
            add("5935");
            add("5978");
            add("7280");
            add("5943");
            add("5993");
        }});
        put(CategoriesList.RESTORANY.label,new ArrayList<>() {{
            add("5811");
            add("5812");
            add("5813");
        }});
        put(CategoriesList.SERVIS_USLUGI.label,new ArrayList<>() {{
            add("5655");
            add("5940");
            add("5941");
        }});
        put(CategoriesList.SPORTTOVARY.label,new ArrayList<>() {{
            add("5655");
            add("5940");
            add("5941");
        }});
        put(CategoriesList.SUVENIRY.label,new ArrayList<>() {{
            add("5947");
        }});
        put(CategoriesList.SUPERMARKETY.label,new ArrayList<>() {{
            add("5297");
            add(" 5412");
            add("5462");
            add("5298");
            add(" 5422");
            add("5499");
            add("5300");
            add("5441");
            add("5715");
            add("5411");
            add("5451");
            add("5921");
        }});
        put(CategoriesList.AVTOUSLUGI.label,new ArrayList<>() {{
            add("5511");
            add("7012");
            add("7538");
            add("5521");
            add("7531");
            add("7542");
            add("5531");
            add("5532");
            add("5533");
            add("7534");
            add("7549");
            add("5571");
            add("7535");
        }});
        put(CategoriesList.ZHILISCHNO_KOMUNALNYE_USLUGI.label,new ArrayList<>() {{
            add("4900");
        }});

    }};

    public InitializeCategory(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @PostConstruct
    public void runAfterStartup() {
        var buff = categoryService.getAll();
        if (buff.size() != data.size()) {
            for (Map.Entry<String, List<String>> entry : data.entrySet()) {
                categoryService.createCategory(entry.getKey(), entry.getValue());
            }
        }

    }
}

