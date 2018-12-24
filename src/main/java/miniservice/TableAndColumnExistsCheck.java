package miniservice;

import java.sql.SQLException;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import app.AppConfig;
import dao.TableDefinitionDao;
import dto.db.ColumnDefinitionDto;
import dto.db.TableDefinitionDto;

@Named
public class TableAndColumnExistsCheck {

    String[] tables = { "mfif_in_temp_interface", "mfif_com_tran_headers_all", "mfif_com_tran_line_all",
	    "mfcm_interface_headers", "mfcm_interface_lines", "mfcm_interface_gl_headers", "mfcm_interface_gl_lines",
	    "mfif_sap_journals", "mfif_out_sap_journals", "mfif_sap_chg_journals", "mfif_out_sap_chg_journals",
	    "mfif_in_sap_journal_status", "mfif_out_foreign_remit_results", "mfap_application_remittance",
	    "mfif_in_sap_journals", "mfif_sap_ledgers", "mfgl_return_journal_status", "mfgl_marc_ledger_data",
	    "mfif_marc_ledger_data_if", "mfgl_ledger_data", "mfif_ledger_data_if", "mfgl_journal_data",
	    "mfif_journal_data_if", "mfif_out_exchange_rates", "mfgl_teller_headers", "mfgl_teller_lines",
	    "mfar_snd_teller_lines", "mfif_trinity_teller_lines_if", "mfar_fb_received_if", "mfcm_se_balance_histories",
	    "mfif_out_se_balances", "mfcm_se_trade_histories", "mfif_out_se_trades", "mfgl_se_current_price_histories",
	    "mfif_out_se_current_prices", "mfmt_mid_company_histories", "mfmt_mid_co_site_histories",
	    "mfmt_mid_at_trn_bank_histries", "mfmt_mid_bank_histories", "mfmt_snd_company_histories",
	    "mfmt_snd_co_site_histories", "mfmt_snd_bank_histories", "mfmt_snd_at_trn_bank_histries",
	    "mfmt_company_histories", "mfmt_company_site_histories", "mfmt_bank_histories",
	    "mfmt_auto_trans_bank_histories", "mfif_out_companies_info", "mfif_out_bank_branches", "mfmt_if_company",
	    "mfmt_if_company_data", "mfmt_mid_estate_histories", "mfmt_snd_estate_histories", "mfmt_estate_histories",
	    "mfif_out_estate_area", "mfmt_mid_estate_areas_histories", "mfif_out_estate_sum",
	    "mfev_mid_estate_sum_histories", "mfmt_estate_area_interfaces", "mfmt_estate_area_datas",
	    "mfif_in_sap_balances", "mfif_out_account_combinations" };

    String[] columns = { "if_upd_datetime", "update_dt", "last_update_date", "update_date_time", "if_ins_datetime",
	    "date_created", "creation_date", "register_date_time", "transmit_process_flag", "if_status" };

    @Inject
    private TableDefinitionDao dao;

    public static void main(String[] args) throws SQLException {
	try (GenericApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class)) {
	    TableAndColumnExistsCheck app = applicationContext.getBean(TableAndColumnExistsCheck.class);
	    app.main();
	}
    }

    private void main() throws SQLException {
	StringBuilder sb = new StringBuilder();
	sb.append("\t");
	for (String column : columns) {
	    sb.append(column);
	    sb.append("\t");
	}
	sb.append("\r\n");
	for (String tableName : tables) {
	    sb.append(tableName);
	    sb.append("\t");
	    TableDefinitionDto metadata = null;
	    try {
		metadata = dao.getMetadata(tableName);
	    } catch (Exception e) {
		// TODO 自動生成された catch ブロック
		e.printStackTrace();
	    }

	    for (String column : columns) {
		boolean exists = false;
		for (ColumnDefinitionDto columnDef : metadata.getColumnList()) {
		    if (column.equals(columnDef.getColumnName())) {
			sb.append("○");
			exists = true;
			break;
		    }
		}
		if (!exists) {
		    sb.append("-");
		}
		sb.append("\t");
	    }
	    sb.append("\r\n");
	}
	System.out.println(sb.toString());
    }

}
