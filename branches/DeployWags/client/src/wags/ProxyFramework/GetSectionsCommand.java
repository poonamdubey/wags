package wags.ProxyFramework;

import wags.WEStatus;

import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.ListBox;

public class GetSectionsCommand extends AbstractServerCall {

	private ListBox sections;
	
	@Override
	protected void handleResponse(Response response)
	{
		WEStatus status = new WEStatus(response);
		
		if (status.getStat() == WEStatus.STATUS_SUCCESS)
		{
			sections.clear();
			
			if (status.getMessageArray().length > 0)
			{
				String[] message = status.getMessageArray();
				String name;
				
				for (String section: message)
				{
					name = section.substring(1, section.length() - 1);
					sections.addItem(name, name);
				}	
			}
			
			else
			{
				sections.addItem(status.getMessage());	
			}
				
		}
			
	}
	
	public GetSectionsCommand(final ListBox sections)
	{
		this.sections = sections;
	}
}
