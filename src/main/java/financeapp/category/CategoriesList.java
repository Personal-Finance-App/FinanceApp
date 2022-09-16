package financeapp.category;

public enum CategoriesList {
    AVIABILETY("Авиабилеты"),
    APTEKI("Аптеки"),
    ARENDA_AVTO("Аренда авто"),
    DOM_REMONT("Дом, ремонт"),
    ZHD_BILETY("Ж/д билеты"),
    KINO("Кино"),
    KRASOTA_("Красота "),
    ZHIVOTNYE("Животные"),
    NALICHNYE("Наличные"),
    ISKUSSTVO("Искусство"),
    NEKOMMERCHESKIE_ORGANIZATSII("Некоммерческие организации"),
    OBRAZOVANIE("Образование"),
    PLATEZHI_V_BJUDZHET("Платежи в бюджет"),
    SVJAZ_TELEKOM("Связь, телеком"),
    RAZVLECHENIJA("Развлечения"),
    TOPLIVO("Топливо"),
    TRANSPORT("Транспорт"),
    TURAGENTSTVA("Турагентства"),
    TSVETY("Цветы"),
    CHASTNYE_USLUGI("Частные услуги"),
    MEDITSINSKIE_USLUGI("Медицинские услуги"),
    MUZYKA("Музыка"),
    ODEZHDA_I_OBUV("Одежда и обувь"),
    OTELI("Отели"),
    DUTY_FREE("Duty Free"),
    FASTFUD("Фастфуд"),
    FINANSOVYE_USLUGI("Финансовые услуги"),
    FOTOVIDEO("Фото/Видео"),
    RAZNYE_TOVARY("Разные товары"),
    RESTORANY("Рестораны"),
    SERVIS_USLUGI("Сервис-услуги"),
    SPORTTOVARY("Спорттовары"),
    SUVENIRY("Сувениры"),
    SUPERMARKETY("Супермаркеты"),
    AVTOUSLUGI("Автоуслуги"),
    PEREVODY("Переводы"),
    ZHILISCHNO_KOMUNALNYE_USLUGI("Жилищно комунальные услуги"),
    DEFAULT("Без Категории");

    public final String label;

    CategoriesList(String label) {
        this.label = label;
    }
}
